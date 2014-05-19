package com.vipshop.mybatis.strategy;

import java.util.Map;

import javax.sql.DataSource;

import com.vipshop.mybatis.common.ShardParam;

public class UserShardStrategy extends ShardStrategy {

	@Override
	public DataSource getTargetDataSource() {
		ShardParam shardParam = getShardParam();
		Long param = (Long) shardParam.getShardValue();
		Map<String, DataSource> map = this.getMysqlDataSources();
		if (param > 100) {
			return map.get("ds2");
		}
		return map.get("ds1");
	}

	@Override
	public String getTargetSql() {
		String targetSql = getSql();
		ShardParam shardParam = getShardParam();
		//
		Long param = (Long) shardParam.getShardValue();
		String tableName = "user_" + (param % 2);
		targetSql = targetSql.replaceAll("\\$\\[user\\]\\$", tableName);
		return targetSql;
	}

}
