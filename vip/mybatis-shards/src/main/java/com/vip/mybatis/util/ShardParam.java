package com.vip.mybatis.util;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 分片参数
 * 
 * @author Anders
 * 
 */
public class ShardParam {

	public static final ShardParam NO_SHARD = new ShardParam();

	/*
	 * XML中配置的分片策略器的name
	 */
	private String name;
	/*
	 * 分片字段的值
	 */
	private String shardValue;
	/*
	 * MyBatis方法的参数
	 */
	private Object params;

	public ShardParam() {
	}

	public ShardParam(String name, String shardValue, Object params) {
		this.name = name;
		this.shardValue = shardValue;
		this.params = params;
	}

	public ShardParam(String name, String shardValue) {
		this.name = name;
		this.shardValue = shardValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShardValue() {
		return shardValue;
	}

	public void setShardValue(String shardValue) {
		this.shardValue = shardValue;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
