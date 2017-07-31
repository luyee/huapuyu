package com.anders.pomelo.databus.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Schema implements Serializable {

	private static final long serialVersionUID = -2380083537651111248L;

	private Map<String, Database> databases;

	private Map<Long, String> tableId2TableName = new HashMap<Long, String>();
	private Map<Long, String> tableId2DatabaseName = new HashMap<Long, String>();
	private Map<Long, Table> tableId2Table = new HashMap<Long, Table>();
	private Map<Long, Database> tableId2Database = new HashMap<Long, Database>();

	public Schema(Map<String, Database> databases) {
		this.databases = databases;
	}

	public Map<String, Database> getDatabases() {
		return this.databases;
	}

	public void addDatabase(String databaseName, Database database) {
		this.databases.put(databaseName, database);
	}

	public void addTableId(Long tableId, String databaseName, String tableName) {
		tableId2DatabaseName.put(tableId, databaseName);
		tableId2TableName.put(tableId, tableName);
		Database database = databases.get(databaseName);
		tableId2Database.put(tableId, database);
		tableId2Table.put(tableId, database.getTable(tableName));
	}

	public String getTableName(Long tableId) {
		return tableId2TableName.get(tableId);
	}

	public String getDatabaseName(Long tableId) {
		return tableId2DatabaseName.get(tableId);
	}

	public Database getDatabase(Long tableId) {
		return tableId2Database.get(tableId);
	}

	public Table getTable(Long tableId) {
		return tableId2Table.get(tableId);
	}
}
