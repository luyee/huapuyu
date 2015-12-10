package com.anders.ethan.sharding.common;

import java.util.Stack;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShardingUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ShardingUtil.class);

	private static final ThreadLocal<Stack<ShardingHolder>> REPOSITORY_HOLDER_STACK = new ThreadLocal<Stack<ShardingHolder>>();

	// private static final ThreadLocal<Map<String, TableShardingStrategy>>
	// TABLE_SHARDING_STRATEGIES = new ThreadLocal<Map<String,
	// TableShardingStrategy>>();

	// public static void setRepositoryShardingKey(String key, boolean
	// transaction) {
	// Stack<ShardingHolder> stack = REPOSITORY_STRATEGY_STACK.get();
	// if (stack == null) {
	// stack = new Stack<ShardingHolder>();
	// }
	// ShardingHolder strategy = new ShardingHolder();
	// // strategy.setRepositoryShardingKey(key);
	// strategy.setTransaction(transaction);
	// stack.push(strategy);
	// REPOSITORY_STRATEGY_STACK.set(stack);
	// }

	// public static String getRepositoryShardingKey() {
	// Stack<ShardingStrategy> stack = REPOSITORY_SHARDING_STRATEGY_STACK.get();
	// if (CollectionUtils.isEmpty(stack)) {
	// return null;
	// }
	// return stack.peek().getRepositoryShardingKey();
	// }

	// public static void removeShardingStrategy() {
	// Stack<ShardingHolder> stack = REPOSITORY_STRATEGY_STACK.get();
	// if (CollectionUtils.isEmpty(stack)) {
	// REPOSITORY_STRATEGY_STACK.remove();
	// return;
	// }
	//
	// ShardingHolder shardingStrategy = stack.peek();
	// // if (shardingStrategy.getCleanType().equals(cleanType)) {
	// stack.pop();
	// if (CollectionUtils.isEmpty(stack)) {
	// REPOSITORY_STRATEGY_STACK.remove();
	// }
	// // }
	// }

	public static void removeCurrent() {
		Stack<ShardingHolder> stack = REPOSITORY_HOLDER_STACK.get();
		if (CollectionUtils.isEmpty(stack)) {
			REPOSITORY_HOLDER_STACK.remove();
			return;
		}
		printStack("before removeCurrent");
		stack.pop();
		printStack("after removeCurrent");
	}

	public static void setReadWriteKey(String key) {
		printStack("before setReadWriteKey");

		Stack<ShardingHolder> stack = REPOSITORY_HOLDER_STACK.get();
		if (stack == null) {
			stack = new Stack<ShardingHolder>();
			REPOSITORY_HOLDER_STACK.set(stack);
		}
		ShardingHolder holder = new ShardingHolder();
		holder.setReadWriteKey(key);
		stack.push(holder);

		printStack("after setReadWriteKey");
	}

	public static String getReadWriteKey() {
		printStack("getReadWriteKey");
		Stack<ShardingHolder> stack = REPOSITORY_HOLDER_STACK.get();
		if (CollectionUtils.isEmpty(stack)) {
			return null;
		}
		return stack.peek().getReadWriteKey();
	}

	private static void printStack(String methodName) {
		Stack<ShardingHolder> stack = REPOSITORY_HOLDER_STACK.get();
		if (CollectionUtils.isEmpty(stack)) {
			LOGGER.debug("{} : keys in stack :", methodName);
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (ShardingHolder shardingHolder : stack) {
			sb.append(shardingHolder.getReadWriteKey() + " ");
		}
		LOGGER.debug("{} : keys in stack : {}", methodName, sb.toString());
	}

	// public static TableShardingStrategy getTableShardingStrategy(String key)
	// {
	// Map<String, TableShardingStrategy> map = TABLE_SHARDING_STRATEGIES.get();
	// if (MapUtils.isEmpty(map)) {
	// return null;
	// }
	// return map.get(key);
	// }
	//
	// public static Map<String, TableShardingStrategy>
	// getTableShardingStrategies() {
	// return TABLE_SHARDING_STRATEGIES.get();
	// }

	// public static void addTableShardingStrategies(String key,
	// TableShardingStrategy value) {
	// Map<String, TableShardingStrategy> map = TABLE_SHARDING_STRATEGIES.get();
	// if (map == null) {
	// map = new HashMap<String, TableShardingStrategy>();
	// TABLE_SHARDING_STRATEGIES.set(map);
	// }
	//
	// if (!map.containsKey(key)) {
	// map.put(key, value);
	// }
	// }

	// public static ShardingHolder getShardingStrategy() {
	// Stack<ShardingHolder> stack = REPOSITORY_STRATEGY_STACK.get();
	// if (CollectionUtils.isEmpty(stack)) {
	// return null;
	// }
	// return stack.peek();
	// }
	//
	// public static boolean hasTransaction() {
	// Stack<ShardingHolder> stack = REPOSITORY_STRATEGY_STACK.get();
	// if (CollectionUtils.isEmpty(stack)) {
	// return false;
	// }
	// return stack.peek().getTransaction();
	// }
}
