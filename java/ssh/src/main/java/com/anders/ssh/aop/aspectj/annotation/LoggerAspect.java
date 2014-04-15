package com.anders.ssh.aop.aspectj.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAspect {

	private static final Logger LOG = LoggerFactory.getLogger(LoggerAspect.class);

	// 切入点 * aspectj.. * .* (..) 表示任意类型的返回类型 包名 包括子包 所有有类 所有方法 任意参数
	// @Pointcut("execution (* aspectj.PersonService.*(..))")
	@Pointcut("execution (* com.anders.ssh.aop.aspectj..*.*(..))")
	private void anyMethod() {
	}

	// 加args(name)后，则只对public void update(String name)这个方法进行控制，其他忽略
	@Before("anyMethod() && args(argName)")
	public void loggerBefore(String argName) {
		LOG.debug("前置通知，参数为：" + argName);
	}

	@AfterReturning(pointcut = "anyMethod()", returning = "result")
	public void loggerAfterReturning(String result) {
		LOG.debug("后置通知，返回参数为：" + result);
	}

	@AfterThrowing(pointcut = "anyMethod()", throwing = "e")
	public void loggerThrowing(Exception e) {
		LOG.debug("异常通知，异常为：" + e.getMessage());
	}

	@After("anyMethod()")
	public void loggerAfter() {
		LOG.debug("最后通知");
	}

	@Around("anyMethod()")
	public Object loggerAround(ProceedingJoinPoint pjp) throws Throwable {
		LOG.debug("环绕通知：前");
		Object object = pjp.proceed();
		LOG.debug("环绕通知：后");
		return object;
	}
}
