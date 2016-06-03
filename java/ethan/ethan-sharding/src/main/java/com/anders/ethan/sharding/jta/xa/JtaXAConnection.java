package com.anders.ethan.sharding.jta.xa;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

public class JtaXAConnection implements XAConnection {

	@Override
	public Connection getConnection() throws SQLException {
		System.out.println(this + "getConnection");
		return null;
	}

	@Override
	public void close() throws SQLException {
		System.out.println(this + "close");
	}

	@Override
	public void addConnectionEventListener(ConnectionEventListener listener) {
		System.out.println(this + "addConnectionEventListener");
	}

	@Override
	public void removeConnectionEventListener(ConnectionEventListener listener) {
		System.out.println(this + "removeConnectionEventListener");
	}

	@Override
	public void addStatementEventListener(StatementEventListener listener) {
		System.out.println(this + "addStatementEventListener");
	}

	@Override
	public void removeStatementEventListener(StatementEventListener listener) {
		System.out.println(this + "removeStatementEventListener");
	}

	@Override
	public XAResource getXAResource() throws SQLException {
		System.out.println(this + "getXAResource");
		return null;
	}

}
