package com.vipshop.mybatis.strategy;

import javax.sql.DataSource;

/**
 * 不分表策略
 * 
 * @author Anders
 * 
 */
public class NoShardStrategy extends ShardStrategy {

	private static final NoShardStrategy INSTANCE = new NoShardStrategy();
	
	private NoShardStrategy() {
	}

	@Override
	public String getTargetSql() {
		return getSql();
	}

	@Override
	public DataSource getTargetDataSource() {
		return getDataSource();
	}

	public static NoShardStrategy getInstance() {
		return INSTANCE;
	}
}
