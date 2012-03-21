package com.anders.crm.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * BO抽象类
 * 
 * @author Anders Zhu
 * 
 */
public abstract class BaseObject implements Serializable {

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract int hashCode();
}
