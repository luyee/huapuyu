package com.anders.pomelo.databus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

public class Table implements Serializable {

	private static final long serialVersionUID = 2474341506181135619L;

	private String databaseName;
	private String name;
	private String charset;
	private List<Column> columns;
	private List<Column> pkColumns;

	public Table(String databaseName, String name, String charset) {
		this.databaseName = databaseName;
		this.name = name;
		this.charset = charset;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public List<Column> getColumns() {
		if (CollectionUtils.isEmpty(columns)) {
			columns = new ArrayList<Column>();
		}

		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public void addColumn(Column column) {
		getColumns().add(column);
	}

	public List<Column> getPkColumns() {
		if (CollectionUtils.isEmpty(pkColumns)) {
			pkColumns = new ArrayList<Column>();
		}

		return pkColumns;
	}

	public void setPkColumns(List<Column> pkColumns) {
		this.pkColumns = pkColumns;
	}

	public void addPkColumn(Column column) {
		getPkColumns().add(column);
	}

}
