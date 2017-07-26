package com.anders.pomelo.databus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {

	private static final long serialVersionUID = 1460662026553702089L;

	private String name;
	private String charset;
	private List<Table> tables = new ArrayList<Table>();

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

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public List<Table> getTables() {
		return tables;
	}

}
