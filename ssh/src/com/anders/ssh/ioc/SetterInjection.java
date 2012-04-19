package com.anders.ssh.ioc;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Setter注入
 * 
 * @author Anders Zhu
 * 
 */
public class SetterInjection {
	private Pojo pojo;

	public Pojo getPojo() {
		return pojo;
	}

	public void setPojo(Pojo pojo) {
		this.pojo = pojo;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
