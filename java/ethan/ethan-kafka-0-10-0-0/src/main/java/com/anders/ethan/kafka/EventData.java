package com.anders.ethan.kafka;

import java.io.Serializable;

public class EventData implements Serializable {

	private static final long serialVersionUID = -4824224707045451638L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
