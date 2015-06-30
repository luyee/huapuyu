package com.anders.ssh.jmx;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.anders.ssh.jmx.AndersMBean;

public class Anders implements AndersMBean {

	private long id;
	private String name;

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String show() {
		return ReflectionToStringBuilder.toString(this);
	}

}
