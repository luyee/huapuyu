package com.vip.mybatis.util;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * shard parameter
 * 
 * @author Anders
 * 
 */
public class ShardParameter {

	public static final ShardParameter NO_SHARD = new ShardParameter();

	/**
	 * shard strategy name
	 */
	private String name;
	/**
	 * shard field value
	 */
	private String value;

	public ShardParameter() {
	}

	public ShardParameter(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
