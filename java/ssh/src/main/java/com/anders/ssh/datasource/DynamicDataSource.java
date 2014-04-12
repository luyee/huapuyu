package com.anders.ssh.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 取得数据源的KEY
 * 
 * @author 陆庆润 创建时间：2009-3-20
 */
public class DynamicDataSource extends AbstractRoutingDataSource implements DisposableBean {
	private final Logger log = Logger.getLogger(DynamicDataSource.class);
	/**
	 * 数据源key的存储控制器
	 */
	private DynamicDataSourceKey dataSourceKey;

	private DataSourceRecoverHeartBeat recoverHeartBeat;

	private Map<String, DataSource> dsMap = new ConcurrentHashMap<String, DataSource>();

	private ExecutorService exe;

	/**
	 * 获得数据源的key
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		String key = StringUtils.EMPTY;
		try {
			key = dataSourceKey.getKey();
		}
		catch (Throwable e) {
			log.error("get data source key fail,will use default data source");
		}
		return key;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return getConnectionFromDataSource(null, null);
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	private Connection getConnectionFromDataSource(String username, String password) throws SQLException {

		Connection con = null;
		DataSource ds = determineTargetDataSource();
		try {
			if (username == null && password == null) {
				con = super.getConnection();
			}
			else {
				con = super.getConnection(username, password);
			}
			validateConnection(con);
		}
		catch (Exception e) {
			// here detected a invalidate datasource
			if (dataSourceKey.isCurrentWriteKey()) { // write datasource failed
				throw new SQLException(e.getMessage());
			}
			// read datasource failed, try to do failover action
			String key = (String) determineCurrentLookupKey();
			dataSourceKey.removeDataSourceKey(key);
			dsMap.put(key, ds);
			executeHeartBeat();

			if (dataSourceKey.hasReadKeyCandidate()) {
				dataSourceKey.reSetKey();
				return getConnectionFromDataSource(username, password);
			}
			throw new SQLException(e.getMessage()); // no more datasource available, throw out exception
		}
		return con;
	}

	private void validateConnection(Connection con) throws SQLException {
		PreparedStatement stmt = con.prepareStatement("select 1"); // test connection is ok
		stmt.executeQuery();
		stmt.close();
	}

	/**
	 * get SQL Connection
	 */
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnectionFromDataSource(username, password);
	}

	public DynamicDataSourceKey getDataSourceKey() {
		return dataSourceKey;
	}

	public void setDataSourceKey(DynamicDataSourceKey dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

	private synchronized void executeHeartBeat() {
		if (recoverHeartBeat == null) {
			recoverHeartBeat = new DataSourceRecoverHeartBeat(this);
			if (exe == null) {
				exe = Executors.newFixedThreadPool(1);
			}
			exe.execute(recoverHeartBeat);
		}
		else {
			if (!recoverHeartBeat.isRuning()) {
				if (exe == null) {
					exe = Executors.newFixedThreadPool(1);
				}
				exe.execute(recoverHeartBeat);
			}
		}
	}

	private synchronized void shutdownHeartBean() {
		if (recoverHeartBeat != null) {
			recoverHeartBeat.close();
		}
		if (exe != null) {
			exe.shutdown();
		}
	}

	/**
	 * Connection status recover heart beat thread.
	 * 
	 * @author xiemalin
	 * 
	 */
	private static class DataSourceRecoverHeartBeat implements Runnable {
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
			Map<String, String> failedDataSources;
			Set<String> dataSourceKeys;
			while (dataSourceKey.hasDataSourceFailed() && !close) {

				failedDataSources = dataSourceKey.getFailedDataSourceKeys();
				dataSourceKeys = failedDataSources.keySet();
				for (String dsKey : dataSourceKeys) {
					try {
						DataSource ds = dynamicDataSource.dsMap.get(dsKey);
						Connection con = ds.getConnection();
						dynamicDataSource.validateConnection(con);
						dataSourceKey.recoverDateSourceKey(dsKey);
						System.out.println("key=" + dsKey + " valid ok!!!!!");
					}
					catch (Exception e) {
						System.out.println("key=" + dsKey + " valid failed");
						// not recover.
					}
				}
				// sleep 1 seconds
				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
				}
			}
			runing = false;
		}
	}

	public void destroy() throws Exception {
		shutdownHeartBean();

	}
}
