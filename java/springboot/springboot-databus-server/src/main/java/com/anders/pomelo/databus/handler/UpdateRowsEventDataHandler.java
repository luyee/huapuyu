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
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;

@Component
public class UpdateRowsEventDataHandler implements EventDataHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(UpdateRowsEventDataHandler.class);

	@Autowired
	private BinlogProps binlogProps;

	@Override
	public void execute(EventData eventData, Schema schema, Connection connection) throws SQLException {
		UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) eventData;

		Long tableId = updateRowsEventData.getTableId();
		BitSet includedColumns = updateRowsEventData.getIncludedColumns();
		// BitSet includedColumnsBeforeUpdate =
		// updateRowsEventData.getIncludedColumnsBeforeUpdate();
		List<Entry<Serializable[], Serializable[]>> rows = updateRowsEventData.getRows();
		String databaseName = schema.getDatabaseName(tableId);
		String tableName = schema.getTableName(tableId);

		if (!binlogProps.getIncludedDatabases().contains(databaseName) || binlogProps.getIgnoredTables().contains(tableName)) {
			return;
		}

		// Database database = schema.getDatabase(tableId);
		Table table = schema.getTable(tableId);
		List<Column> columns = table.getColumns();
		Map<Column, Integer> pkColumns = table.getPkColumns();
		TimeZone tz = TimeZone.getDefault();

		StringBuilder sets = new StringBuilder();
		for (int i = 0; i < columns.size(); i++) {
			if (includedColumns.get(i)) {
				sets.append("`" + columns.get(i).getName() + "`=?,");
			}
		}

		StringBuilder where = new StringBuilder();
		for (Entry<Column, Integer> entry : pkColumns.entrySet()) {
			if (!includedColumns.get(entry.getValue())) {
				// TODO Anders 处理所有的异常信息
				throw new RuntimeException("includedColumns should contain `" + entry.getKey().getName() + "`");
			}
			where.append("`" + entry.getKey().getName() + "`=? AND ");
		}

		String sql = String.format("UPDATE `%s` SET %s WHERE %s", tableName, StringUtils.stripEnd(sets.toString(), ","), StringUtils.stripEnd(where.toString(), " AND "));
		LOGGER.warn(sql);

		for (Entry<Serializable[], Serializable[]> row : rows) {
			PreparedStatement stmt = connection.prepareStatement(sql);

			for (int i = 0; i < row.getValue().length; i++) {
				if (row.getValue()[i] != null) {
					if (row.getValue()[i] instanceof String) {
						stmt.setString(i + 1, (String) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof Integer) {
						stmt.setInt(i + 1, (Integer) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof BigDecimal) {
						stmt.setBigDecimal(i + 1, (BigDecimal) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof Boolean) {
						stmt.setBoolean(i + 1, (boolean) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof byte[]) {
						stmt.setBytes(i + 1, (byte[]) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof Double) {
						stmt.setDouble(i + 1, (Double) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof Float) {
						stmt.setFloat(i + 1, (Float) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof Long) {
						stmt.setLong(i + 1, (Long) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof Short) {
						stmt.setShort(i + 1, (Short) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof Blob) {
						throw new RuntimeException("unsupported the type : " + Blob.class.getTypeName());
					} else if (row.getValue()[i] instanceof Clob) {
						throw new RuntimeException("unsupported the type : " + Clob.class.getTypeName());
					} else if (row.getValue()[i] instanceof Byte) {
						throw new RuntimeException("unsupported the type : " + Byte.class.getTypeName());
					} else if (row.getValue()[i] instanceof BitSet) {
						stmt.setBoolean(i + 1, ((BitSet) row.getValue()[i]).get(0));
					} else if (row.getValue()[i] instanceof Date) {
						stmt.setDate(i + 1, new Date(((Date) row.getValue()[i]).getTime() - tz.getRawOffset()));
					} else if (row.getValue()[i] instanceof Time) {
						stmt.setTime(i + 1, new Time(((Time) row.getValue()[i]).getTime() - tz.getRawOffset()));
					} else if (row.getValue()[i] instanceof Timestamp) {
						stmt.setTimestamp(i + 1, (Timestamp) row.getValue()[i]);
					} else if (row.getValue()[i] instanceof java.util.Date) {
						stmt.setTimestamp(i + 1, new Timestamp(((java.util.Date) row.getValue()[i]).getTime() - tz.getRawOffset()));
					} else {
						throw new RuntimeException("unsupported the type : " + row.getValue()[i].getClass().getTypeName());
					}
				} else {
					// stmt.setNull(i + 1, JDBCType.VARCHAR.ordinal());
					stmt.setObject(i + 1, null);
				}
			}

			int i = row.getValue().length;
			for (Entry<Column, Integer> entry : pkColumns.entrySet()) {
				if (row.getValue()[entry.getValue()] != null) {
					if (row.getValue()[entry.getValue()] instanceof String) {
						stmt.setString(i + 1, (String) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof Integer) {
						stmt.setInt(i + 1, (Integer) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof BigDecimal) {
						stmt.setBigDecimal(i + 1, (BigDecimal) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof Boolean) {
						stmt.setBoolean(i + 1, (boolean) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof byte[]) {
						stmt.setBytes(i + 1, (byte[]) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof Double) {
						stmt.setDouble(i + 1, (Double) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof Float) {
						stmt.setFloat(i + 1, (Float) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof Long) {
						stmt.setLong(i + 1, (Long) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof Short) {
						stmt.setShort(i + 1, (Short) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof Blob) {
						throw new RuntimeException("unsupported the type : " + Blob.class.getTypeName());
					} else if (row.getValue()[entry.getValue()] instanceof Clob) {
						throw new RuntimeException("unsupported the type : " + Clob.class.getTypeName());
					} else if (row.getValue()[entry.getValue()] instanceof Byte) {
						throw new RuntimeException("unsupported the type : " + Byte.class.getTypeName());
					} else if (row.getValue()[entry.getValue()] instanceof BitSet) {
						stmt.setBoolean(i + 1, ((BitSet) row.getValue()[entry.getValue()]).get(0));
					} else if (row.getValue()[entry.getValue()] instanceof Date) {
						stmt.setDate(i + 1, new Date(((Date) row.getValue()[entry.getValue()]).getTime() - tz.getRawOffset()));
					} else if (row.getValue()[entry.getValue()] instanceof Time) {
						stmt.setTime(i + 1, new Time(((Time) row.getValue()[entry.getValue()]).getTime() - tz.getRawOffset()));
					} else if (row.getValue()[entry.getValue()] instanceof Timestamp) {
						stmt.setTimestamp(i + 1, (Timestamp) row.getValue()[entry.getValue()]);
					} else if (row.getValue()[entry.getValue()] instanceof java.util.Date) {
						stmt.setTimestamp(i + 1, new Timestamp(((java.util.Date) row.getValue()[entry.getValue()]).getTime() - tz.getRawOffset()));
					} else {
						throw new RuntimeException("unsupported the type : " + row.getValue()[entry.getValue()].getClass().getTypeName());
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