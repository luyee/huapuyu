package com.anders.ssh.aop.spring;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;

public class AfterAdvice implements AfterReturningAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(AfterAdvice.class);

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		LOG.debug("后置通知：" + method.getName());
	}
}
