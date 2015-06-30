package com.vip.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.vip.datasource.util.Constants;

public class DynamicDataSource extends AbstractRoutingDataSource implements DisposableBean {

	private final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

	private DynamicDataSourceKey dataSourceKey;

	private DataSourceRecoverHeartBeat recoverHeartBeat;

	private Map<String, DataSource> key2DataSourceMap = new ConcurrentHashMap<String, DataSource>();

	private ExecutorService executorService;

	@Override
	protected Object determineCurrentLookupKey() {
		String key = StringUtils.EMPTY;
		try {
			key = dataSourceKey.getKey();
			logger.debug("dynamic datasource switch to " + key);
		}
		catch (Throwable e) {
			logger.error("get datasource key fail, will use default datasource");
		}
		return key;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getConnectionFromDataSource(null, null);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnectionFromDataSource(username, password);
	}

	private Connection getConnectionFromDataSource(String username, String password) throws SQLException {

		Connection connection = null;
		DataSource ds = determineTargetDataSource();
		try {
			if (username == null && password == null) {
				connection = super.getConnection();
			}
			else {
				connection = super.getConnection(username, password);
			}
			validateConnection(connection);
		}
		catch (Exception e) {
			if (dataSourceKey.isCurrentWriteKey()) {
				throw new SQLException(e.getMessage());
			}
			String key = (String) determineCurrentLookupKey();
			dataSourceKey.removeDataSourceKey(key);
			key2DataSourceMap.put(key, ds);
			executeHeartBeat();

			if (dataSourceKey.hasReadKey()) {
				dataSourceKey.resetKey();
				return getConnectionFromDataSource(username, password);
			}
			throw new SQLException(e.getMessage());
		}
		return connection;
	}

	private void validateConnection(Connection connection) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(Constants.VALIDATE_SQL);
		stmt.executeQuery();
		stmt.close();
	}

	@Override
	public void destroy() throws Exception {
		shutdownHeartBeat();
	}

	private synchronized void executeHeartBeat() {
		if (recoverHeartBeat == null) {
			recoverHeartBeat = new DataSourceRecoverHeartBeat(this);
			if (executorService == null) {
				executorService = Executors.newFixedThreadPool(1);
			}
			executorService.execute(recoverHeartBeat);
		}
		else {
			if (!recoverHeartBeat.isRuning()) {
				if (executorService == null) {
					executorService = Executors.newFixedThreadPool(1);
				}
				executorService.execute(recoverHeartBeat);
			}
		}
	}

	private synchronized void shutdownHeartBeat() {
		if (recoverHeartBeat != null) {
			recoverHeartBeat.close();
		}
		if (executorService != null) {
			executorService.shutdown();
		}
	}

	public DynamicDataSourceKey getDataSourceKey() {
		return dataSourceKey;
	}

	public void setDataSourceKey(DynamicDataSourceKey dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

	/**
	 * datasource heart beat
	 * 
	 * @author Anders
	 * 
	 */
	private static class DataSourceRecoverHeartBeat implements Runnable {

		private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceRecoverHeartBeat.class);

		private DynamicDataSource dynamicDataSource;
		private boolean runing;
		private boolean close = false;

		public boolean isRuning() {
			return runing;
		}

		public void close() {
			close = true;
		}

		public DataSourceRecoverHeartBeat(DynamicDataSource dynamicDataSource) {
			this.dynamicDataSource = dynamicDataSource;
		}

		public void run() {
			runing = true;
			DynamicDataSourceKey dataSourceKey = dynamicDataSource.getDataSourceKey();
			// Map<String, String> failedDataSources;
			// Set<String> dataSourceKeys;
			while (dataSourceKey.hasFailedDataSource() && !close) {

				// failedDataSources = dataSourceKey.getFailedDataSourceKeys();
				// dataSourceKeys = failedDataSources.keySet();
				for (String key : dataSourceKey.getFailedDataSourceKeys().keySet()) {
					try {
						DataSource ds = dynamicDataSource.key2DataSourceMap.get(key);
						Connection connection = ds.getConnection();
						dynamicDataSource.validateConnection(connection);
						dataSourceKey.recoverDateSourceKey(key);
						LOGGER.debug("key = " + key + " valid ok");
					}
					catch (Exception e) {
						LOGGER.error("key = " + key + " valid failed");
					}
				}
				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
			}
			runing = false;
		}
	}
}
