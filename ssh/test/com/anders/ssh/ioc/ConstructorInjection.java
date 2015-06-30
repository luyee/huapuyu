package com.anders.ssh.ioc;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 构造器注入
 * 
 * @author Anders
 * 
 */
public class ConstructorInjection {
	private Pojo pojo;
	private int salary;
	private String department;

	public ConstructorInjection(Pojo pojo, int salary, String department) {
		this.pojo = pojo;
		this.salary = salary;
		this.department = department;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
