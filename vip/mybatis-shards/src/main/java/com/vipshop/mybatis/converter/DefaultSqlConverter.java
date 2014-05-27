package com.vipshop.mybatis.converter;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.springframework.stereotype.Component;

import com.vipshop.mybatis.common.StrategyHolder;
import com.vipshop.mybatis.strategy.NoShardStrategy;
import com.vipshop.mybatis.strategy.ShardStrategy;

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
