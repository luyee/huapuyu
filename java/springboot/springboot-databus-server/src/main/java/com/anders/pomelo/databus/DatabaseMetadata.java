package com.anders.pomelo.databus;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseMetadata {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DatabaseMetadata.class); 
	
	private final static DatabaseMetadata DATABASE_METADATA = new DatabaseMetadata();
	private Connection connection;
	
	private DatabaseMetadata() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
		}
		 
//		Connection conn;
//		try {
//			conn = DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306","root","123");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  Statement stmt = conn.createStatement();
	}
	
	public static DatabaseMetadata getInstance() {
		return DATABASE_METADATA;
	}
}
