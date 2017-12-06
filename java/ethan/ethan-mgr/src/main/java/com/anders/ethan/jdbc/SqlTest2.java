package com.anders.ethan.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class SqlTest2 {

	@Test
	public void test() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			e.printStackTrace();
		}

		String url = "jdbc:mysql://192.168.56.181:3306,192.168.56.183:3306,192.168.56.182:3306/anders?failOverReadOnly=false&useSSL=false";
		String username = "root";
		String password = "123";
		try {
			for (int i = 1; i <= 100000; i++) {
				Connection con = DriverManager.getConnection(url, username, password);
				Statement stmt = null;
				try {
					stmt = con.createStatement();
					stmt.executeUpdate("insert into t1 (c1, c2) value (" + i + ", '" + i + "')");
				} catch (SQLException e) {
					System.out.println("**********" + i + "**********");
				} finally {
					stmt.close();
					con.close();
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		}

		System.in.read();
	}
}