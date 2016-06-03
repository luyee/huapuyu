package com.anders.ethan.sharding.jta.xa;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.XAConnection;
import javax.sql.XADataSource;

import com.alibaba.druid.pool.DruidDataSource;

public class JtaXADataSource implements XADataSource {

	private DruidDataSource druidDataSource;

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public XAConnection getXAConnection() throws SQLException {
		System.out.println(this + "getXAConnection");
		return null;
	}

	@Override
	public XAConnection getXAConnection(String user, String password)
			throws SQLException {
		System.out.println(this + "getXAConnection");
		return null;
	}

	public DruidDataSource getDruidDataSource() {
		return druidDataSource;
	}

	public void setDruidDataSource(DruidDataSource druidDataSource) {
		this.druidDataSource = druidDataSource;
	}

}
