package com.vip.mybatis.converter;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.springframework.stereotype.Component;

import com.vip.mybatis.strategy.NoShardStrategy;
import com.vip.mybatis.strategy.ShardStrategy;
import com.vip.mybatis.util.StrategyHolder;

@Component("sqlConverter")
public class DefaultSqlConverter implements SqlConverter {

	public String convert(String sql, StatementHandler statementHandler) {
		ShardStrategy strategy = StrategyHolder.getShardStrategy();
		Map<String, ShardStrategy> strategies = StrategyHolder.getShardStrategies();

		if (MapUtils.isEmpty(strategies)) {
			if (strategy == null || strategy instanceof NoShardStrategy) {
				return sql;
			}
		}

		return strategy.getTargetSql(sql);
	}
}