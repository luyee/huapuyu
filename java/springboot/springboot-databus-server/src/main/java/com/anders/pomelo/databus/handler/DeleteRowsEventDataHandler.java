package com.anders.pomelo.databus.handler;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
							} else {
								System.out.println(row[entry.getValue()].getClass());
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
