package com.anders.pomelo.databus.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.anders.pomelo.databus.model.Schema;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;

@Component
public class QueryEventDataHandler implements EventDataHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(QueryEventDataHandler.class);

	private final static Set<String> IGNORED_SQL = new HashSet<String>(Arrays.asList(new String[] { "BEGIN" }));

	@Override
	public void execute(EventData eventData, Schema schema, Connection connection) throws SQLException {
		QueryEventData queryEventData = (QueryEventData) eventData;

		String sql = queryEventData.getSql();
		if (IGNORED_SQL.contains(sql)) {
			return;
		}

		Statement stmt = connection.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();

		LOGGER.warn(queryEventData.getSql());

	}
}
