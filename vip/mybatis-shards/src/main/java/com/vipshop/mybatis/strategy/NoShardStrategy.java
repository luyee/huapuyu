package com.vipshop.mybatis.strategy;

import javax.sql.DataSource;

/**
 * 不分表策�?
 * 
 * @author Anders
 * 
 */
public class NoShardStrategy extends ShardStrategy {

	public static final NoShardStrategy INSTANCE = new NoShardStrategy();

	@Override
	public String getTargetSql() {
		return getSql();
	}

	@Override
	public DataSource getTargetDataSource() {
		return getDefaultDataSource();
	}

}
