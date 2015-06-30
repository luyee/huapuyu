package com.anders.ssh.webservice.cxf.ws;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Singer implements Serializable {
	private static final long serialVersionUID = -1395889415857111008L;

	private String name;
	private int age;

	public Singer() {
	}

	public Singer(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
