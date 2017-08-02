package com.anders.pomelo.databus.handler;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anders.pomelo.databus.cfg.BinlogProps;
import com.anders.pomelo.databus.model.Column;
import com.anders.pomelo.databus.model.Schema;
import com.anders.pomelo.databus.model.Table;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;

@Component
public class DeleteRowsEventDataHandler implements EventDataHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(DeleteRowsEventDataHandler.class);

	@Autowired
	private BinlogProps binlogProps;

	@Override
	public void execute(EventData eventData, Schema schema, Connection connection) throws SQLException {
		DeleteRowsEventData deleteRowsEventData = (DeleteRowsEventData) eventData;

		Long tableId = deleteRowsEventData.getTableId();
		BitSet includedColumns = deleteRowsEventData.getIncludedColumns();
		List<Serializable[]> rows = deleteRowsEventData.getRows();
		String databaseName = schema.getDatabaseName(tableId);
		String tableName = schema.getTableName(tableId);
		// Database database = schema.getDatabase(tableId);
		Table table = schema.getTable(tableId);
		// List<Column> columns = table.getColumns();
		Map<Column, Integer> pkColumns = table.getPkColumns();
		TimeZone tz = TimeZone.getDefault();

		StringBuilder where = new StringBuilder();
		for (Entry<Column, Integer> entry : pkColumns.entrySet()) {
			if (!includedColumns.get(entry.getValue())) {
				// TODO Anders 处理所有的异常信息
				throw new RuntimeException("includedColumns should contain `" + entry.getKey().getName() + "`");
			}
			where.append("`" + entry.getKey().getName() + "`=? AND ");
		}

		if (binlogProps.getIncludedDatabases().contains(databaseName) && !binlogProps.getIgnoredTables().contains(tableName)) {
			String sql = String.format("DELETE FROM `%s` WHERE %s", tableName, StringUtils.stripEnd(where.toString(), " AND "));

			for (Serializable[] row : rows) {
				PreparedStatement stmt = connection.prepareStatement(sql);
				LOGGER.warn(sql);

				int i = 0;
				for (Entry<Column, Integer> entry : pkColumns.entrySet()) {
					if (row[entry.getValue()] != null) {
						if (row[entry.getValue()] instanceof String) {
							stmt.setString(i + 1, (String) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof Integer) {
							stmt.setInt(i + 1, (Integer) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof BigDecimal) {
							stmt.setBigDecimal(i + 1, (BigDecimal) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof Boolean) {
							stmt.setBoolean(i + 1, (boolean) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof byte[]) {
							stmt.setBytes(i + 1, (byte[]) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof Double) {
							stmt.setDouble(i + 1, (Double) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof Float) {
							stmt.setFloat(i + 1, (Float) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof Long) {
							stmt.setLong(i + 1, (Long) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof Short) {
							stmt.setShort(i + 1, (Short) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof Blob) {
							throw new RuntimeException("unsupported the type : " + Blob.class.getTypeName());
						} else if (row[entry.getValue()] instanceof Clob) {
							throw new RuntimeException("unsupported the type : " + Clob.class.getTypeName());
						} else if (row[entry.getValue()] instanceof Byte) {
							throw new RuntimeException("unsupported the type : " + Byte.class.getTypeName());
						} else if (row[entry.getValue()] instanceof BitSet) {
							stmt.setBoolean(i + 1, ((BitSet) row[entry.getValue()]).get(0));
						} else if (row[entry.getValue()] instanceof Date) {
							stmt.setDate(i + 1, new Date(((Date) row[entry.getValue()]).getTime() - tz.getRawOffset()));
						} else if (row[entry.getValue()] instanceof Time) {
							stmt.setTime(i + 1, new Time(((Time) row[entry.getValue()]).getTime() - tz.getRawOffset()));
						} else if (row[entry.getValue()] instanceof Timestamp) {
							stmt.setTimestamp(i + 1, (Timestamp) row[entry.getValue()]);
						} else if (row[entry.getValue()] instanceof java.util.Date) {
							stmt.setTimestamp(i + 1, new Timestamp(((java.util.Date) row[entry.getValue()]).getTime() - tz.getRawOffset()));
						} else {
							throw new RuntimeException("unsupported the type : " + row[entry.getValue()].getClass().getTypeName());
						}
					} else {
						// stmt.setNull(i + 1, JDBCType.VARCHAR.ordinal());
						stmt.setObject(i + 1, null);
					}
					i++;
				}

				stmt.execute();
				stmt.close();
			}
		}
	}
}
