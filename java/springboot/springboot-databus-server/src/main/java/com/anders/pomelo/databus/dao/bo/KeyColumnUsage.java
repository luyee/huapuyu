package com.anders.pomelo.databus.dao.bo;

import java.io.Serializable;

public class KeyColumnUsage implements Serializable {

	private static final long serialVersionUID = -7055489710206745870L;

	private String tableName;
	private String columnName;
	private Long ordinalPosition;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Long getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(Long ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

}