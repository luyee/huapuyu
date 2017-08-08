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
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

@Component
public class WriteRowsEventDataHandler implements EventDataHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(WriteRowsEventDataHandler.class);

	@Autowired
	private BinlogProps binlogProps;

	@Override
	public void execute(EventData eventData, Schema schema, Connection connection) throws SQLException {
		WriteRowsEventData writeRowsEventData = (WriteRowsEventData) eventData;

		Long tableId = writeRowsEventData.getTableId();
		BitSet includedColumns = writeRowsEventData.getIncludedColumns();
		List<Serializable[]> rows = writeRowsEventData.getRows();
		String databaseName = schema.getDatabaseName(tableId);
		String tableName = schema.getTableName(tableId);

		if (!binlogProps.getIncludedDatabases().contains(databaseName) || binlogProps.getIgnoredTables().contains(tableName)) {
			return;
		}

		// Database database = schema.getDatabase(tableId);
		Table table = schema.getTable(tableId);
		List<Column> columns = table.getColumns();
		// List<Column> pkColumns = table.getPkColumns();
		TimeZone tz = TimeZone.getDefault();

		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();
		for (int i = 0; i < columns.size(); i++) {
			if (includedColumns.get(i)) {
				fields.append("`" + columns.get(i).getName() + "`,");
				values.append("?,");
			}
		}

		String sql = String.format("INSERT INTO `%s` (%s) VALUES (%s)", tableName, StringUtils.stripEnd(fields.toString(), ","), StringUtils.stripEnd(values.toString(), ","));

		for (Serializable[] row : rows) {
			LOGGER.warn(sql);
			PreparedStatement stmt = connection.prepareStatement(sql);

			for (int i = 0; i < row.length; i++) {
				// System.out.println(row[i].getClass().getTypeName());

				if (row[i] != null) {
					if (row[i] instanceof String) {
						stmt.setString(i + 1, (String) row[i]);
					} else if (row[i] instanceof Integer) {
						stmt.setInt(i + 1, (Integer) row[i]);
					} else if (row[i] instanceof BigDecimal) {
						stmt.setBigDecimal(i + 1, (BigDecimal) row[i]);
					} else if (row[i] instanceof Boolean) {
						stmt.setBoolean(i + 1, (boolean) row[i]);
					} else if (row[i] instanceof byte[]) {
						stmt.setBytes(i + 1, (byte[]) row[i]);
					} else if (row[i] instanceof Double) {
						stmt.setDouble(i + 1, (Double) row[i]);
					} else if (row[i] instanceof Float) {
						stmt.setFloat(i + 1, (Float) row[i]);
					} else if (row[i] instanceof Long) {
						stmt.setLong(i + 1, (Long) row[i]);
					} else if (row[i] instanceof Short) {
						stmt.setShort(i + 1, (Short) row[i]);
					} else if (row[i] instanceof Blob) {
						// stmt.setBlob(i + 1, (Blob) row[i]);
						throw new RuntimeException("unsupported the type : " + Blob.class.getTypeName());
					} else if (row[i] instanceof Clob) {
						// stmt.setClob(i + 1, (Clob) row[i]);
						throw new RuntimeException("unsupported the type : " + Clob.class.getTypeName());
					} else if (row[i] instanceof Byte) {
						// stmt.setByte(i + 1, (Byte) row[i]);
						throw new RuntimeException("unsupported the type : " + Byte.class.getTypeName());
					} else if (row[i] instanceof BitSet) {
						stmt.setBoolean(i + 1, ((BitSet) row[i]).get(0));
					} else if (row[i] instanceof Date) {
						stmt.setDate(i + 1, new Date(((Date) row[i]).getTime() - tz.getRawOffset()));
					} else if (row[i] instanceof Time) {
						stmt.setTime(i + 1, new Time(((Time) row[i]).getTime() - tz.getRawOffset()));
					} else if (row[i] instanceof Timestamp) {
						stmt.setTimestamp(i + 1, (Timestamp) row[i]);
					} else if (row[i] instanceof java.util.Date) {
						stmt.setTimestamp(i + 1, new Timestamp(((java.util.Date) row[i]).getTime() - tz.getRawOffset()));
					} else {
						throw new RuntimeException("unsupported the type : " + row[i].getClass().getTypeName());
					}
				} else {
					// stmt.setNull(i + 1, JDBCType.VARCHAR.ordinal());
					stmt.setObject(i + 1, null);
				}
			}

			stmt.execute();
			stmt.close();
		}
	}
}
