package com.vip.mybatis.util;

import com.vip.mybatis.strategy.ShardStrategy;

/**
 * 分片策略ThreadLocal
 * 
 * @author Anders
 * 
 */
public class StrategyHolder {

	private static ThreadLocal<ShardStrategy> STRATEGY_HOLDER = new ThreadLocal<ShardStrategy>();

	public static ShardStrategy getShardStrategy() {
		return STRATEGY_HOLDER.get();
	}

	public static void removeShardStrategy() {
		STRATEGY_HOLDER.remove();
	}

	public static void setShardStrategy(ShardStrategy shardStrategy) {
		STRATEGY_HOLDER.set(shardStrategy);
	}

}
