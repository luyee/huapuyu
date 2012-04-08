package com.anders.crm.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Base Business Object
 * 
 * @author Anders Zhu
 * 
 */
@SuppressWarnings("serial")
public abstract class BaseBO implements Serializable {

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	// @Override
	// public abstract boolean equals(Object object);

	// @Override
	// public abstract int hashCode();
}
