package com.vipshop.mybatis.common;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;

import com.vipshop.mybatis.strategy.ShardStrategy;

/**
 * 事务信息、数据源、事务状态Holder
 * 
 * @author Anders
 * 
 */
public class SqlSessionFactoryHolder {
	private static ThreadLocal<Map<DataSource, SqlSessionFactory>> DS2SSF = new ThreadLocal<Map<DataSource, SqlSessionFactory>>();
	private static ThreadLocal<Map<String, SqlSessionFactory>> DSID2SSF = new ThreadLocal<Map<String, SqlSessionFactory>>();
	private static ThreadLocal<Map<String, ShardStrategy>> NAME2STRATEGY = new ThreadLocal<Map<String, ShardStrategy>>();

	public static void addDataSource2SqlSessionFactory(DataSource dataSource, SqlSessionFactory sqlSessionFactory) {
		Map<DataSource, SqlSessionFactory> map = DS2SSF.get();
		if (map == null) {
			map = new HashMap<DataSource, SqlSessionFactory>();
			DS2SSF.set(map);
		}

		map.put(dataSource, sqlSessionFactory);
	}

	public static Map<DataSource, SqlSessionFactory> getDataSource2SqlSessionFactory() {
		return DS2SSF.get();
	}

	public static Map<String, SqlSessionFactory> getDataSourceId2SqlSessionFactory() {
		return DSID2SSF.get();
	}

	public static void addDataSourceId2SqlSessionFactory(String dataSourceId, SqlSessionFactory sqlSessionFactory) {
		Map<String, SqlSessionFactory> map = DSID2SSF.get();
		if (map == null) {
			map = new HashMap<String, SqlSessionFactory>();
			DSID2SSF.set(map);
		}

		map.put(dataSourceId, sqlSessionFactory);
	}
	
	public static Map<String, ShardStrategy> getStrategyName2ShardStrategy() {
		return NAME2STRATEGY.get();
	}

	public static void addStrategyName2ShardStrategy(String strategyName, ShardStrategy shardStrategy) {
		Map<String, ShardStrategy> map = NAME2STRATEGY.get();
		if (map == null) {
			map = new HashMap<String, ShardStrategy>();
			NAME2STRATEGY.set(map);
		}

		map.put(strategyName, shardStrategy);
	}
}
