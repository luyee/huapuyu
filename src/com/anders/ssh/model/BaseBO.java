package com.anders.ssh.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public abstract class BaseBO implements Serializable {

	@Override
	public abstract boolean equals(Object object);

	@Override
	public abstract int hashCode();

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
