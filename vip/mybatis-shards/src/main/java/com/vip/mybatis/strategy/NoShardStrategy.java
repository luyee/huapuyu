package com.vip.mybatis.strategy;

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
	public String getTargetSql(String sql) {
		return sql;
	}

	@Override
	public String getTargetDynamicDataSource() {
		return getDefaultDataSource();
	}

	public static NoShardStrategy getInstance() {
		return INSTANCE;
	}
}
