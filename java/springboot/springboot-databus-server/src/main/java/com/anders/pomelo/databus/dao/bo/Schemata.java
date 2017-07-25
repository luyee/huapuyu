package com.anders.pomelo.databus.dao.bo;

import java.io.Serializable;

public class Schemata implements Serializable {

	private static final long serialVersionUID = 3186649422000609938L;
	
	private String schemaName;
	private String defaultCharacterSetName;

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getDefaultCharacterSetName() {
		return defaultCharacterSetName;
	}

	public void setDefaultCharacterSetName(String defaultCharacterSetName) {
		this.defaultCharacterSetName = defaultCharacterSetName;
	}

}