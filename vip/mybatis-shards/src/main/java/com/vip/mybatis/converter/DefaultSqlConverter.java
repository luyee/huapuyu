package com.vip.mybatis.converter;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.springframework.stereotype.Component;

import com.vip.mybatis.strategy.NoShardStrategy;
import com.vip.mybatis.strategy.ShardStrategy;
import com.vip.mybatis.util.StrategyHolder;

@Component("sqlConverter")
public class DefaultSqlConverter implements SqlConverter {

	public String convert(String sql, StatementHandler statementHandler) {
		ShardStrategy strategy = StrategyHolder.getShardStrategy();
		if (strategy == null || strategy instanceof NoShardStrategy) {
//	    	  return "insert into user_0 (id, name) values (?, ?)";
			
			return sql;
		}
		return strategy.getTargetSql();
	}

}
