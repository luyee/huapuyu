package com.anders.ethan.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class SqlTest {

	@Test
	public void test() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			e.printStackTrace();
		}

		ExecutorService pool = Executors.newFixedThreadPool(100);

		String url = "jdbc:mysql://192.168.56.181:3306,192.168.56.183:3306,192.168.56.182:3306/anders?failOverReadOnly=false&useSSL=false";
		String username = "root";
		String password = "123";
		for (int i = 1; i <= 100; i++) {
			pool.execute(new MyThread(url, username, password, i));
		}

		System.in.read();
	}
}

class MyThread extends Thread {

	private int starter;
	private String url;
	private String username;
	private String password;

	public MyThread(String url, String username, String password, int starter) {
		this.starter = starter;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public void run() {
		Connection con = null;
		Statement stmt = null;
		for (int i = starter; i < 10000000; i = i + 100) {
			try {
				con = DriverManager.getConnection(url, username, password);
				stmt = con.createStatement();
				stmt.executeUpdate("insert into t1 (c1, c2) value (" + i + ", '" + starter + "')");
			} catch (Exception e) {
				System.out.println(getName() + " " + i);
			} finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
