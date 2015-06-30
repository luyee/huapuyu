package com.anders.vote.mybatis.test;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.vote.mybatis.OptimisticLockingInterceptor;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class TestPlugin1 implements Interceptor {

	private Logger log = LoggerFactory.getLogger(OptimisticLockingInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object obj) {
		return Plugin.wrap(obj, this);
	}

	@Override
	public void setProperties(Properties props) {
		if (props != null && props.getProperty("mapper") != null) {
			log.info("Found mapper property, using class: " + props.getProperty("mapper"));
		}
	}
}
