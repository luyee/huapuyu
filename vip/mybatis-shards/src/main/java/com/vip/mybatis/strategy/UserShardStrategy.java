package com.vip.mybatis.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.mybatis.util.ShardParameter;

/**
 * Demo分表策略（根据参数范围确定数据源和分表）
 * 
 * @author Anders
 * 
 */
public class UserShardStrategy extends ShardStrategy {

	private final Logger logger = LoggerFactory.getLogger(UserShardStrategy.class);

	// TODO Anders 想办法把这里直接通过字符串获取shard datasource改掉
	@Override
	public String getTargetDynamicDataSource() {
		ShardParameter shardParameter = getShardParameter();
		Long param = Long.parseLong(shardParameter.getValue());
		if (param > 100 && param <= 200) {
			logger.debug("shard datasource switch to dataSource2");
			return "dataSource2";
		}
		else if (param > 200) {
			logger.debug("shard datasource switch to dataSource3");
			return "dataSource3";
		}

		logger.debug("shard datasource switch to " + getDefaultDataSource());
		return getDefaultDataSource();
	}

	@Override
	public String getTargetSql(String sql) {
		ShardParameter shardParameter = getShardParameter();
		Long param = Long.parseLong(shardParameter.getValue());
		String tableName = "user" + (param % 2);
		return sql.replaceAll("\\$\\[user\\]\\$", tableName);
	}

}
