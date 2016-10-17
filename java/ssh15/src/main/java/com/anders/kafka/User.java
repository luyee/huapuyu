package com.anders.kafka;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = -155355630752553522L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
