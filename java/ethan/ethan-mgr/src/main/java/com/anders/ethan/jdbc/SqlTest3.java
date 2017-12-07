package com.anders.ethan.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 单线程连接池测试
 * 
 * @author Anders
 *
 */
public class SqlTest3 {

	@Test
	public void test() throws Exception {
		// Properties properties = new Properties();
		// properties.setProperty("url",
		// "jdbc:mysql://192.168.56.181:3306,192.168.56.183:3306,192.168.56.182:3306/anders?failOverReadOnly=false&useSSL=false");
		// properties.setProperty("username", "root");
		// properties.setProperty("password", "123");
		// properties.setProperty("maxActive", "1");
		// properties.setProperty("initialSize", "1");
		// properties.setProperty("minIdle", "1");
		// properties.setProperty("maxWait", "5000");

		DruidDataSource ds = new DruidDataSource();
		ds.setUrl(
				"jdbc:mysql://192.168.56.181:3306,192.168.56.183:3306,192.168.56.182:3306/anders?failOverReadOnly=false&useSSL=false");
		ds.setUsername("root");
		ds.setPassword("123");
		ds.setInitialSize(1);
		ds.setMaxActive(1);
		ds.setMinIdle(1);
		ds.setMaxWait(5000);
		ds.init();

		try {
			for (int i = 1; i <= 100000; i++) {
				Connection con = ds.getConnection();
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

		ds.close();
	}
}