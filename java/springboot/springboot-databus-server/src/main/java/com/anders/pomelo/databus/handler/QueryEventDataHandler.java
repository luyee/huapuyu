package com.anders.pomelo.databus.handler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anders.pomelo.databus.cfg.BinlogProps;
import com.anders.pomelo.databus.cfg.SlavedbProps;
import com.anders.pomelo.databus.model.Schema;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;

@Component
public class QueryEventDataHandler implements EventDataHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(QueryEventDataHandler.class);

	private final static Set<String> IGNORED_SQL = new HashSet<String>(Arrays.asList(new String[] { "BEGIN" }));

	@Autowired
	private BinlogProps binlogProps;
	@Autowired
	private SlavedbProps slavedbProps;

	@Override
	public void execute(EventData eventData, Schema schema, Connection connection) throws SQLException {
		QueryEventData queryEventData = (QueryEventData) eventData;

		// TODO Anders 分析sql语句，ignored table不执行
		String sql = queryEventData.getSql();
		if (IGNORED_SQL.contains(sql)) {
			return;
		}

		// TODO Anders 此处代码需要优化，使用语法解析器过滤表名
		for (Iterator<String> iterator = binlogProps.getIncludedDatabases().iterator(); iterator.hasNext();) {
			String dbName = iterator.next();
			if (StringUtils.isNotBlank(dbName)) {
				sql = StringUtils.replaceAll(sql, dbName, slavedbProps.getDbname());
			}
		}

		Statement stmt = connection.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();

		LOGGER.warn("old sql : {}", queryEventData.getSql());
		LOGGER.warn("new sql : {}", sql);
	}
}
