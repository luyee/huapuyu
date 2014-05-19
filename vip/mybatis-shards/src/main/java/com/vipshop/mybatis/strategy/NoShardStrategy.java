package com.vipshop.mybatis.strategy;

import java.util.Map;

import javax.sql.DataSource;

//TODO Anders Zhu ： 这个类要改写 
public class NoShardStrategy extends ShardStrategy {

	public static final NoShardStrategy INSTANCE = new NoShardStrategy();

	@Override
	public String getTargetSql() {
		return getSql();
	}

	@Override
	public DataSource getTargetDataSource() {
		Map<String, DataSource> map = this.getMysqlDataSources();
		return map.get("ds1");
	}

}
