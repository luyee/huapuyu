package com.vipshop.mybatis.strategy;

import java.util.Map;

import javax.sql.DataSource;

import com.vipshop.mybatis.common.ShardParam;

/**
 * Demo分表策略（根据参数范围确定数据源和分表）
 * 
 * @author Anders
 * 
 */
public class UserShardStrategy extends ShardStrategy {

	@Override
	public DataSource getTargetDataSource() {
		ShardParam shardParam = getShardParam();
		Long param = (Long) shardParam.getShardValue();
		Map<String, DataSource> map = this.getShardDataSources();
		if (param > 100 && param <= 200) {
			return map.get("dataSource_mysql_1");
		}
		else if (param > 200) {
			return map.get("dataSource_mysql_2");
		}
		return getDataSource();
	}

	@Override
	public String getTargetSql() {
		String targetSql = getSql();
		ShardParam shardParam = getShardParam();
		Long param = (Long) shardParam.getShardValue();
		String tableName = "user_" + (param % 2);
		targetSql = targetSql.replaceAll("\\$\\[user\\]\\$", tableName);
		return targetSql;
	}

}
