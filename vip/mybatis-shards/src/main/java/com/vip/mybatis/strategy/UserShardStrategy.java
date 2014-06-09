package com.vip.mybatis.strategy;

import com.vip.mybatis.util.ShardParameter;

/**
 * Demo分表策略（根据参数范围确定数据源和分表）
 * 
 * @author Anders
 * 
 */
public class UserShardStrategy extends ShardStrategy {

	@Override
	public String getTargetDynamicDataSource() {
		ShardParameter shardParameter = getShardParameter();
		Long param = Long.parseLong(shardParameter.getValue());
		if (param > 100 && param <= 200) {
			System.out.println("dataSource2");
			return "dataSource2";
		}
		else if (param > 200) {
			System.out.println("dataSource3");
			return "dataSource3";
		}
		
		System.out.println("dataSource");
		return getDefaultDataSource();
	}

	@Override
	public String getTargetSql() {
		String targetSql = getSql();
		ShardParameter shardParameter = getShardParameter();
		Long param = Long.parseLong(shardParameter.getValue());
		String tableName = "user" + (param % 2);
		targetSql = targetSql.replaceAll("\\$\\[table\\]\\$", tableName);
		return targetSql;
	}

}
