package com.anders.ethan.log.cat.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 677621861559907877L;

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("{'id':%d, 'name':'%s'}", id, name);
	}

}
