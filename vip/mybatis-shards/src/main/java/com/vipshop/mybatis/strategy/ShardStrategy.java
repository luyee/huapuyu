package com.vipshop.mybatis.strategy;

import java.util.Map;

import javax.sql.DataSource;

import com.vipshop.mybatis.common.ShardParam;

/**
 * 分表策略抽象类
 * 
 * @author Anders Zhu
 * 
 */
public abstract class ShardStrategy {

	private static final ThreadLocal<Map<String, DataSource>> oracleDataSources = new ThreadLocal<Map<String, DataSource>>();
	private static final ThreadLocal<Map<String, DataSource>> mysqlDataSources = new ThreadLocal<Map<String, DataSource>>();
	private static final ThreadLocal<String> sql = new ThreadLocal<String>();
	private static final ThreadLocal<ShardParam> shardParam = new ThreadLocal<ShardParam>();

	public Map<String, DataSource> getOracleDataSources() {
		return oracleDataSources.get();
	}

	public void setOracleDataSources(Map<String, DataSource> oracleDataSources) {
		ShardStrategy.oracleDataSources.set(oracleDataSources);
	}

	public Map<String, DataSource> getMysqlDataSources() {
		return mysqlDataSources.get();
	}

	public void setMysqlDataSources(Map<String, DataSource> mysqlDataSources) {
		ShardStrategy.mysqlDataSources.set(mysqlDataSources);
	}

	public String getSql() {
		return sql.get();
	}

	public void setSql(String sql) {
		ShardStrategy.sql.set(sql);
	}

	public ShardParam getShardParam() {
		return shardParam.get();
	}

	public void setShardParam(ShardParam shardParam) {
		ShardStrategy.shardParam.set(shardParam);
	}

	public abstract DataSource getTargetDataSource();

	public abstract String getTargetSql();
}
