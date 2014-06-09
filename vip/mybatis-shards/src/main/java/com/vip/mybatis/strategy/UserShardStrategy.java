package com.vip.mybatis.strategy;

import com.vip.mybatis.util.ShardParam;

/**
 * Demo分表策略（根据参数范围确定数据源和分表）
 * 
 * @author Anders
 * 
 */
public class UserShardStrategy extends ShardStrategy {

	@Override
	public String getTargetDynamicDataSource() {
		ShardParam shardParam = getShardParam();
		Long param = Long.parseLong(shardParam.getShardValue());
		if (param > 100 && param <= 200) {
			return "dataSource2";
		}
		else if (param > 200) {
			return "dataSource3";
		}
		return getDefaultDataSource();
	}

	@Override
	public String getTargetSql() {
		String targetSql = getSql();
		ShardParam shardParam = getShardParam();
		Long param = Long.parseLong(shardParam.getShardValue());
		String tableName = "user" + (param % 2);
		targetSql = targetSql.replaceAll("\\$\\[table\\]\\$", tableName);
		return targetSql;
	}

}
