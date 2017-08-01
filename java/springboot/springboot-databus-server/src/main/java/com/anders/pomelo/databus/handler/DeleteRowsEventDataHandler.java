package com.anders.pomelo.databus.handler;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
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

	@Autowired
	private BinlogProps binlogProps;

	@Override
	public void execute(EventData eventData, Schema schema) {
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

		StringBuilder where = new StringBuilder();
		for (Entry<Column, Integer> entry : pkColumns.entrySet()) {
			if (!includedColumns.get(entry.getValue())) {
				// TODO Anders 处理所有的异常信息
				throw new RuntimeException("includedColumns should contain `" + entry.getKey().getName() + "`");
			}
			where.append("`" + entry.getKey().getName() + "`=? AND ");
		}

		if (binlogProps.getIncludedDatabases().contains(databaseName)) {
			try {
				String sql = String.format("DELETE FROM `%s` WHERE %s", tableName,
						StringUtils.stripEnd(where.toString(), " AND "));

				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/databus_to?useUnicode=true&characterEncoding=UTF-8&useSSL=false", "root",
						"123");

				for (Serializable[] row : rows) {
					PreparedStatement stmt = conn.prepareStatement(sql);
					System.out.println(sql);

					int i = 0;
					for (Entry<Column, Integer> entry : pkColumns.entrySet()) {
						if (row[entry.getValue()] != null) {
							if (row[entry.getValue()] instanceof String) {
								stmt.setString(i + 1, (String) row[entry.getValue()]);
							} else if (row[entry.getValue()] instanceof Integer) {
								stmt.setInt(i + 1, (Integer) row[entry.getValue()]);
							}  else if (row[entry.getValue()] instanceof BigDecimal) {
								stmt.setBigDecimal(i + 1, (BigDecimal) row[i]);
							} else if (row[entry.getValue()] instanceof Boolean) {
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
						i++;
					}

					stmt.execute();
					stmt.close();
				}

				conn.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
