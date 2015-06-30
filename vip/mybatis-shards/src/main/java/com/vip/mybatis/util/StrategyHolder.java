package com.vip.mybatis.util;

import java.util.HashMap;
import java.util.Map;

import com.vip.mybatis.strategy.ShardStrategy;

/**
 * 分片策略ThreadLocal
 * 
 * @author Anders
 * 
 */
public class StrategyHolder {

	private static ThreadLocal<ShardStrategy> STRATEGY_HOLDER = new ThreadLocal<ShardStrategy>();
	private static final ThreadLocal<Map<String, ShardStrategy>> STRATEGIES_HOLDER = new ThreadLocal<Map<String, ShardStrategy>>();

	public static ShardStrategy getShardStrategy() {
		return STRATEGY_HOLDER.get();
	}

	public static void removeShardStrategy() {
		STRATEGY_HOLDER.remove();
		STRATEGIES_HOLDER.remove();
	}

	public static void setShardStrategy(ShardStrategy shardStrategy) {
		STRATEGY_HOLDER.set(shardStrategy);
	}

	public static ShardStrategy getShardStrategy(String key) {
		Map<String, ShardStrategy> map = STRATEGIES_HOLDER.get();
		if (map == null) {
			return null;
		}
		return map.get(key);
	}

	public static Map<String, ShardStrategy> getShardStrategies() {
		return STRATEGIES_HOLDER.get();
	}

	public static void addShardStrategies(String key, ShardStrategy value) {
		Map<String, ShardStrategy> map = STRATEGIES_HOLDER.get();
		if (map == null) {
			map = new HashMap<String, ShardStrategy>();
			STRATEGIES_HOLDER.set(map);
		}
		map.put(key, value);
	}

}
