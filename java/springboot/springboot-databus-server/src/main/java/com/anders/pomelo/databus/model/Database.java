package com.anders.pomelo.databus.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

public class Database implements Serializable {

	private static final long serialVersionUID = 1460662026553702089L;

	private String name;
	private String charset;
	private Map<String, Table> tables;

	public Database(String name, String charset) {
		this.name = name;
		this.charset = charset;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Table> getTables() {
		if (MapUtils.isEmpty(tables)) {
			tables = new HashMap<String, Table>();
		}
		return tables;
	}

	public void setTables(Map<String, Table> tables) {
		this.tables = tables;
	}

	public void addTable(String tableName, Table table) {
		getTables().put(tableName, table);
	}

	public Table getTable(String tableName) {
		return getTables().get(tableName);
	}

	public Table removeTable(String tableName) {
		return getTables().remove(tableName);
	}

}
