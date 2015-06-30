package com.anders.ssh.aop.spring;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

public class ThrowAdvice implements ThrowsAdvice {

	private static final Logger LOG = LoggerFactory.getLogger(ThrowAdvice.class);

	public void afterThrowing(Method method, Object[] args, Object target, Throwable subclass) {
		LOG.debug("异常: " + method.getName());
	}
}
