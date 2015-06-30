package com.vip.mybatis.strategy;

import com.vip.datasource.util.Constants;
import com.vip.mybatis.util.ShardParameter;

/**
 * 分表策略抽象类
 * 
 * @author Anders
 * 
 */
public abstract class ShardStrategy {

	// private static final ThreadLocal<DataSource> dataSource = new ThreadLocal<DataSource>();
	// private static final ThreadLocal<Map<String, DataSource>> shardDataSources = new ThreadLocal<Map<String, DataSource>>();
	private static final ThreadLocal<ShardParameter> shardParameter = new ThreadLocal<ShardParameter>();

	// public DataSource getDataSource() {
	// return dataSource.get();
	// }
	//
	// public void setDataSource(DataSource dataSource) {
	// ShardStrategy.dataSource.set(dataSource);
	// }
	//
	// public Map<String, DataSource> getShardDataSources() {
	// return shardDataSources.get();
	// }
	//
	// public void setShardDataSources(Map<String, DataSource> shardDataSources) {
	// ShardStrategy.shardDataSources.set(shardDataSources);
	// }

	public String getDefaultDataSource() {
		return Constants.DEFAULT_DYNAMIC_DS;
	}

	public ShardParameter getShardParameter() {
		return shardParameter.get();
	}

	public void setShardParameter(ShardParameter shardParameter) {
		ShardStrategy.shardParameter.set(shardParameter);
	}

	// public abstract DataSource getTargetDataSource();
	public abstract String getTargetDynamicDataSource();

	public abstract String getTargetSql(String sql);
}
