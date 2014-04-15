package com.anders.ssh.aop.spring;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class BeforeAdvice implements MethodBeforeAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(BeforeAdvice.class);

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		LOG.debug("前置通知：" + method.getName());
	}
}
