package com.vip.mybatis.plugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.vip.mybatis.converter.SqlConverter;

/**
 * 分表插件
 * 
 * @author Anders
 * 
 */
@Component("shardPlugin")
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class ShardPlugin implements Interceptor {

	private static final Logger log = LoggerFactory.getLogger(ShardPlugin.class);

	@Resource(name = "sqlConverter")
	private SqlConverter sqlConverter;

	public SqlConverter getSqlConverter() {
		return sqlConverter;
	}

	public void setSqlConverter(SqlConverter sqlConverter) {
		this.sqlConverter = sqlConverter;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

		// 原始SQL
		String sql = statementHandler.getBoundSql().getSql();
		if (log.isDebugEnabled()) {
			log.debug("Original SQL [ " + sql + " ]");
		}

		// 经过转换后的SQL
		String targetSql = sqlConverter.convert(sql, statementHandler);
		if (log.isDebugEnabled()) {
			log.debug("Converted SQL [ " + targetSql + " ]");
		}

		if (!sql.equals(targetSql)) {
			// ReflectionUtils.setFieldValue(statementHandler.getBoundSql(),
			// "sql", targetSql);
			Field field = BoundSql.class.getDeclaredField("sql");
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, statementHandler.getBoundSql(), targetSql);
		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
