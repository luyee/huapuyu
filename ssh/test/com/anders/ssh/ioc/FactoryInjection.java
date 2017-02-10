package com.anders.ssh.ioc;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 静态工厂注入
 * 
 * @author Anders Zhu
 * 
 */
public class FactoryInjection {
	private Pojo pojo;
	private int salary;
	private String department;

	private FactoryInjection() {
	}

	public static FactoryInjection createInstance(Pojo pojo, int salary, String department) {
		FactoryInjection fi = new FactoryInjection();
		fi.pojo = pojo;
		fi.salary = salary;
		fi.department = department;
		return fi;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
