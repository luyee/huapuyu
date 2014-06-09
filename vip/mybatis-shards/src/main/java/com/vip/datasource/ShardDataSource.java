package com.vip.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.AbstractDataSource;

public class ShardDataSource extends AbstractDataSource {

	// private final Logger logger = LoggerFactory.getLogger(ShardDataSource.class);

	private DynamicDataSource dataSource;

	// @Override
	// protected Object determineCurrentLookupKey() {
	// return dataSource.determineCurrentLookupKey();
	// }

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return dataSource.getConnection(username, password);
	}

	public DynamicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DynamicDataSource dataSource) {
		this.dataSource = dataSource;
	}

}
