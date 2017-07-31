package com.anders.pomelo.databus.handler;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

	@Autowired
	private BinlogProps binlogProps;

	@Override
	public void execute(EventData eventData, Schema schema) {
		WriteRowsEventData writeRowsEventData = (WriteRowsEventData) eventData;

		Long tableId = writeRowsEventData.getTableId();
		BitSet includedColumns = writeRowsEventData.getIncludedColumns();
		List<Serializable[]> rows = writeRowsEventData.getRows();
		String databaseName = schema.getDatabaseName(tableId);
		String tableName = schema.getTableName(tableId);
		// Database database = schema.getDatabase(tableId);
		Table table = schema.getTable(tableId);
		List<Column> columns = table.getColumns();
		// List<Column> pkColumns = table.getPkColumns();

		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();
		for (int i = 0; i < columns.size(); i++) {
			if (includedColumns.get(i)) {
				fields.append("`" + columns.get(i).getName() + "`,");
				values.append("?,");
			}
		}

		if (binlogProps.getIncludedDatabases().contains(databaseName)) {
			try {
				String sql = String.format("INSERT INTO `%s` (%s) VALUES (%s)", tableName,
						StringUtils.stripEnd(fields.toString(), ","), StringUtils.stripEnd(values.toString(), ","));
				System.out.println(sql);

				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/databus_to?useUnicode=true&characterEncoding=UTF-8&useSSL=false", "root",
						"123");

				for (Serializable[] row : rows) {
					PreparedStatement stmt = conn.prepareStatement(sql);

					for (int i = 0; i < row.length; i++) {
						if (row[i] != null) {
							if (row[i] instanceof String) {
								stmt.setString(i + 1, (String) row[i]);
							} else if (row[i] instanceof Integer) {
								stmt.setInt(i + 1, (Integer) row[i]);
							} else {
								System.out.println(row[i].getClass());
							}
						} else {
							// stmt.setNull(i + 1, JDBCType.VARCHAR.ordinal());
							stmt.setObject(i + 1, null);
						}
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
