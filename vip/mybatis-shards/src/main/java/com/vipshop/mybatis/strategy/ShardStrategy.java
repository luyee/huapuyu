package com.vipshop.mybatis.strategy;

import java.util.Map;

import javax.sql.DataSource;

import com.vipshop.mybatis.common.ShardParam;

/**
 * 分表策略抽象类
 * 
 * @author Anders
 * 
 */
public abstract class ShardStrategy {

	private static final ThreadLocal<DataSource> dataSource = new ThreadLocal<DataSource>();
	private static final ThreadLocal<Map<String, DataSource>> shardDataSources = new ThreadLocal<Map<String, DataSource>>();
	private static final ThreadLocal<String> sql = new ThreadLocal<String>();
	private static final ThreadLocal<ShardParam> shardParam = new ThreadLocal<ShardParam>();

	public DataSource getDataSource() {
		return dataSource.get();
	}

	public void setDataSource(DataSource dataSource) {
		ShardStrategy.dataSource.set(dataSource);
	}

	public Map<String, DataSource> getShardDataSources() {
		return shardDataSources.get();
	}

	public void setShardDataSources(Map<String, DataSource> shardDataSources) {
		ShardStrategy.shardDataSources.set(shardDataSources);
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
