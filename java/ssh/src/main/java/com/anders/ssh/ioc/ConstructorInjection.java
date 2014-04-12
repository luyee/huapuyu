package com.anders.ssh.ioc;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 构造器注入
 * 
 * @author Anders Zhu
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

	public Pojo getPojo() {
		return pojo;
	}

	public void setPojo(Pojo pojo) {
		this.pojo = pojo;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
