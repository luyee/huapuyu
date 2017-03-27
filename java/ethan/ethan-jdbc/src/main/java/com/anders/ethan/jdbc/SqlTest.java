package com.anders.ethan.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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

		String url = "jdbc:mysql://172.16.1.29:3306/eif_market";
		String username = "root";
		String password = "123";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, username, password);

			for (int i = 0; i < 100000; i++) {
				query(con);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			con.close();
		}

	}

	private void query(Connection con) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			long begin = new Date().getTime();
			rs = stmt
					.executeQuery("SELECT * FROM t_market_coupon_user WHERE expripration_time >= '2020-02-13 00:00:00'");
			System.out.println("time : " + (new Date().getTime() - begin));

			// while (rs.next()) {
			// System.out.println(rs.getLong(1) + ";" +
			// rs.getDate("expripration_time"));
			// }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
