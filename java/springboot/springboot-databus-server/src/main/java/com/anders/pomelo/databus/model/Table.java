package com.anders.pomelo.databus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {

	private static final long serialVersionUID = 2474341506181135619L;

	private String databaseName;
	private String name;
	private String charset;
	private List<Column> columns = new ArrayList<Column>();

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
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

}
