package com.anders.ssh.aop.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AroundAdvice implements MethodInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(AroundAdvice.class);

	public Object invoke(MethodInvocation invocation) throws Throwable {
		LOG.debug("环绕开始：" + invocation.getMethod().getName());
		Object result = invocation.proceed();
		LOG.debug("环绕结束：" + invocation.getMethod().getName());
		return result;
	}
}
