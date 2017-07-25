package com.anders.pomelo.databus.dao.bo;

import java.io.Serializable;

public class Tables implements Serializable {

	private static final long serialVersionUID = 5861413427631301102L;

	private String tableName;
	private String characterSetName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCharacterSetName() {
		return characterSetName;
	}

	public void setCharacterSetName(String characterSetName) {
		this.characterSetName = characterSetName;
	}

}