package com.anders.ssh.ioc;

/**
 * 构造器注入
 * 
 * @author Anders
 * 
 */
public class ConstructorInjection
{
	private Pojo pojo;
	private int salary;
	private String department;

	public ConstructorInjection(Pojo pojo, int salary, String department)
	{
		this.pojo = pojo;
		this.salary = salary;
		this.department = department;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + "[id : " + this.pojo.getId() + "; name : " + this.pojo.getName() + "; score : " + this.pojo.getScore() + "; salary : " + this.salary + "; department : " + this.department + "]";
	}
}
