package com.anders.ssh.mybatis;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({ @Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class ExecutorInterceptor implements Interceptor {

	private final static Log LOGGER = LogFactory.getLog(ExecutorInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Executor executor = (Executor) invocation.getTarget();

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameterObject = invocation.getArgs()[1];
		RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
		ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];
		BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);

		Object object = invocation.proceed();

		Statement stmt = null;
		List<Object> list = new ArrayList<Object>();
		try {
			Configuration configuration = mappedStatement.getConfiguration();
			StatementHandler handler = configuration.newStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
			Connection connection = executor.getTransaction().getConnection();
			stmt = handler.prepare(connection);
			handler.parameterize(stmt);
			list = handler.query(stmt, resultHandler);
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}

		if (object instanceof List<?>) {
			list.addAll((List<?>) object);
		}

		return list;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
