package com.anders.ssh.ioc;

public class FactoryInjection
{
	private Pojo pojo;
	private int salary;
	private String department;

	private FactoryInjection()
	{
	}

	public static FactoryInjection createInstance(Pojo pojo, int salary, String department)
	{
		FactoryInjection fi = new FactoryInjection();
		fi.pojo = pojo;
		fi.salary = salary;
		fi.department = department;
		return fi;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + "[id : " + this.pojo.getId() + "; name : " + this.pojo.getName() + "; score : " + this.pojo.getScore() + "; salary : " + this.salary + "; department : " + this.department + "]";
	}
}
