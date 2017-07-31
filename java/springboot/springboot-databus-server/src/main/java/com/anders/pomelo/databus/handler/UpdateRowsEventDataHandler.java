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
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;

@Component
public class UpdateRowsEventDataHandler implements EventDataHandler {

	@Autowired
	private BinlogProps binlogProps;

	@Override
	public void execute(EventData eventData, Schema schema) {
		UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) eventData;

		Long tableId = updateRowsEventData.getTableId();
		BitSet includedColumns = updateRowsEventData.getIncludedColumns();
		// BitSet includedColumnsBeforeUpdate =
		// updateRowsEventData.getIncludedColumnsBeforeUpdate();
		List<Entry<Serializable[], Serializable[]>> rows = updateRowsEventData.getRows();
		String databaseName = schema.getDatabaseName(tableId);
		String tableName = schema.getTableName(tableId);
		// Database database = schema.getDatabase(tableId);
		Table table = schema.getTable(tableId);
		List<Column> columns = table.getColumns();
		Map<Column, Integer> pkColumns = table.getPkColumns();

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

		if (binlogProps.getIncludedDatabases().contains(databaseName)) {
			try {
				String sql = String.format("UPDATE `%s` SET %s WHERE %s", tableName,
						StringUtils.stripEnd(sets.toString(), ","), StringUtils.stripEnd(where.toString(), " AND "));
				System.out.println(sql);

				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/databus_to?useUnicode=true&characterEncoding=UTF-8&useSSL=false",
						"root", "123");

				for (Entry<Serializable[], Serializable[]> row : rows) {
					PreparedStatement stmt = conn.prepareStatement(sql);

					for (int i = 0; i < row.getValue().length; i++) {
						if (row.getValue()[i] != null) {
							if (row.getValue()[i] instanceof String) {
								stmt.setString(i + 1, (String) row.getValue()[i]);
							} else if (row.getValue()[i] instanceof Integer) {
								stmt.setInt(i + 1, (Integer) row.getValue()[i]);
							} else {
								System.out.println(row.getValue()[i].getClass());
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
							} else {
								System.out.println(row.getValue()[entry.getValue()].getClass());
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