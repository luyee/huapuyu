package com.anders.pomelo.databus.handler;

import java.sql.Connection;
import java.sql.SQLException;

import com.anders.pomelo.databus.model.Schema;
import com.github.shyiko.mysql.binlog.event.EventData;

public interface EventDataHandler {

	public void execute(EventData eventData, Schema schema, Connection connection) throws SQLException;
}
