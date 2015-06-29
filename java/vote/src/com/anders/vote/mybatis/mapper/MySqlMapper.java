package com.anders.vote.mybatis.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.anders.vote.mybatis.EntityWrapper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException;

public class MySqlMapper implements Mapper {

	@Override
	public Object getCurrentEntityVersionInDatabase(Connection connection, EntityWrapper entityWrapper) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement("SELECT " + entityWrapper.getVersionColumnName() + " FROM " + entityWrapper.getTableName() + " WHERE id = ? FOR UPDATE");
			stmt.setObject(1, entityWrapper.getIdentity());
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.first()) {
				Object databaseVersion = rs.getObject(1);
				rs.close();
				return databaseVersion;
			}
			else {
				return null;
			}
		}
		catch (MySQLTransactionRollbackException e) {
			throw new RuntimeException();
		}
		finally {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		}
	}

	@Override
	public boolean isCompatible(Connection connection) {
		return false;
	}
}
