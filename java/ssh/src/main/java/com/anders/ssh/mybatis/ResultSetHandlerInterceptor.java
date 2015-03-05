package com.anders.ssh.mybatis;

import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

@Intercepts({ @Signature(method = "handleResultSets", type = ResultSetHandler.class, args = { Statement.class }) })
public class ResultSetHandlerInterceptor implements Interceptor {

	private final static Log log = LogFactory.getLog(ResultSetHandlerInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();

		Statement statement = (Statement) invocation.getArgs()[0];
		// while (statement.getMoreResults()) {
		// System.out.println(statement.getResultSet());
		// }

		Object object = invocation.proceed();

		return object;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
