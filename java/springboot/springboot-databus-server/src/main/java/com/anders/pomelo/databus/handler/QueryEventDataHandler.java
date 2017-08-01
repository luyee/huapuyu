package com.anders.pomelo.databus.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.anders.pomelo.databus.model.Schema;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;

@Component
public class QueryEventDataHandler implements EventDataHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(QueryEventDataHandler.class);

	@Override
	public void execute(EventData eventData, Schema schema, Connection connection) throws SQLException {
		QueryEventData queryEventData = (QueryEventData) eventData;

		String sql = queryEventData.getSql();
		if (sql.equalsIgnoreCase("BEGIN")) {
			return;
		}

		Statement stmt = connection.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();

		LOGGER.error(queryEventData.getSql());

	}
}
