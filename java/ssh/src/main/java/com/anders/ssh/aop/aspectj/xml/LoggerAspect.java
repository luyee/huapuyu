package com.anders.ssh.aop.aspectj.xml;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAspect {

	private static final Logger LOG = LoggerFactory.getLogger(LoggerAspect.class);

	public void loggerBefore() {
		LOG.debug("前置通知");
	}

	// public void loggerAfterReturning(String result)
	public void loggerAfterReturning() {
		LOG.debug("后置通知");
	}

	// public void loggerThrowing(Exception e)
	public void loggerThrowing() {
		LOG.debug("异常通知");
	}

	public void loggerAfter() {
		LOG.debug("最后通知");
	}

	public Object loggerAround(ProceedingJoinPoint pjp) throws Throwable {
		LOG.debug("环绕通知：前");
		Object object = pjp.proceed();
		LOG.debug("环绕通知：后");
		return object;
	}
}
