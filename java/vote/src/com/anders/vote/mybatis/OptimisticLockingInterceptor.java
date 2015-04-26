package com.anders.vote.mybatis;

import java.sql.Connection;
import java.sql.SQLTransactionRollbackException;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.vote.mybatis.mapper.Mapper;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class OptimisticLockingInterceptor implements Interceptor {

	private Logger log = LoggerFactory.getLogger(OptimisticLockingInterceptor.class);

	private Mapper mapper;

	private void updateImpl(Connection connection, EntityWrapper entityWrapper) throws Throwable {
		try {
			Object currentEntityVersion = mapper.getCurrentEntityVersionInDatabase(connection, entityWrapper);
			if (currentEntityVersion == null) {
				throw new RuntimeException();
			}
			else {
				if (entityWrapper.isStale(currentEntityVersion)) {
					throw new RuntimeException();
				}
				else {
					entityWrapper.incrementVersion();
				}
			}
		}
		catch (SQLTransactionRollbackException e) {
			throw new RuntimeException();
		}
	}

	private void deleteImpl(Connection connection, EntityWrapper entityWrapper) throws Throwable {
		try {
			Object currentEntityVersion = mapper.getCurrentEntityVersionInDatabase(connection, entityWrapper);
			if (currentEntityVersion == null) {
				throw new RuntimeException();
			}
			else if (entityWrapper.isStale(currentEntityVersion)) {
				throw new RuntimeException();
			}
		}
		catch (SQLTransactionRollbackException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		EntityWrapper entityWrapper = null;
		MappedStatement ms = null;

		Executor executor = (Executor) invocation.getTarget();
		Connection connection = executor.getTransaction().getConnection();

		if (mapper == null) {
			log.info("Mapper not initialized, trying to find appropriate mapper using database metadata.");
			mapper = MapResolver.getMapper(connection);
		}

		if (invocation.getArgs() != null && invocation.getArgs().length > 0 && invocation.getArgs()[0] != null && invocation.getArgs()[0] instanceof MappedStatement) {

			ms = (MappedStatement) invocation.getArgs()[0];
			Object obj = invocation.getArgs()[1];

			if (EntityWrapper.hasOptimisticLockingAnnotation(obj)) {
				if (ms.getSqlCommandType() == SqlCommandType.UPDATE) {
					entityWrapper = new EntityWrapper(obj);
					updateImpl(connection, entityWrapper);
				}
				else if (ms.getSqlCommandType() == SqlCommandType.INSERT) {
					entityWrapper = new EntityWrapper(obj);
					entityWrapper.initVersion();
				}
				else if (ms.getSqlCommandType() == SqlCommandType.DELETE) {
					entityWrapper = new EntityWrapper(obj);
					deleteImpl(connection, entityWrapper);
				}
			}
			else if (ms.getSqlCommandType() == SqlCommandType.DELETE || ms.getSqlCommandType() == SqlCommandType.INSERT || ms.getSqlCommandType() == SqlCommandType.UPDATE) {
				if (obj == null) {
					log.debug("Optimistic locking can not be applied! Parameter is null.");
				}
				else {
					log.debug("Optimistic locking can not be applied! Parameter type '" + obj.getClass().getName() + "' does not have the annotation @OptimisticLocking.");
				}
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		if (arg0 != null && arg0.getProperty("mapper") != null) {
			log.info("Found mapper property, using class: " + arg0.getProperty("mapper"));
			mapper = MapResolver.getMapper(arg0.getProperty("mapper"));
		}
	}
}