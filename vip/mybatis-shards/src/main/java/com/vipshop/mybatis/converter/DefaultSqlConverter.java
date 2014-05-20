package com.vipshop.mybatis.converter;

import org.apache.ibatis.executor.statement.StatementHandler;

import com.vipshop.mybatis.spring.StrategyHolder;
import com.vipshop.mybatis.strategy.NoShardStrategy;
import com.vipshop.mybatis.strategy.ShardStrategy;

public class DefaultSqlConverter implements SqlConverter {

	public String convert(String sql, StatementHandler statementHandler) {
		ShardStrategy strategy = StrategyHolder.getShardStrategy();
		if (strategy == null || strategy instanceof NoShardStrategy) {
			return sql;
		}
		return strategy.getTargetSql();
	}

}
