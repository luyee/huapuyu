package com.anders.ssh.mybatis;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

import com.anders.ssh.log.LogCallPK;

@Intercepts({ @Signature(method = "query", type = StatementHandler.class, args = { Statement.class, ResultHandler.class }), @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class StatementHandlerInterceptor implements Interceptor {

	private final static Log log = LogFactory.getLog(StatementHandlerInterceptor.class);

	// @Resource
	private LogCallPK logCallPK;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		BoundSql boundSql = statementHandler.getBoundSql();
		log.error(logCallPK.getLogCallPK() + " : " + boundSql.getSql());
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	public LogCallPK getLogCallPK() {
		return logCallPK;
	}

	public void setLogCallPK(LogCallPK logCallPK) {
		this.logCallPK = logCallPK;
	}

}
