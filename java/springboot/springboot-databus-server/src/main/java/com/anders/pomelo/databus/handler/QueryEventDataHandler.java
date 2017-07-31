package com.anders.pomelo.databus.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import com.anders.pomelo.databus.model.Schema;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;

@Component
public class QueryEventDataHandler implements EventDataHandler {

	@Override
	public void execute(EventData eventData, Schema schema) {
		QueryEventData queryEventData = (QueryEventData) eventData;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/databus_to?useUnicode=true&characterEncoding=UTF-8&useSSL=false", "root", "123");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(queryEventData.getSql());
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println(queryEventData.getSql());

	}
}
