package com.anders.ssh.aop.aspectj.xml;

import org.aspectj.lang.ProceedingJoinPoint;

public class LoggerAspect
{
	public void loggerBefore()
	{
		System.out.println("前置通知");
	}

	// public void loggerAfterReturning(String result)
	public void loggerAfterReturning()
	{
		System.out.println("后置通知");
	}

	// public void loggerThrowing(Exception e)
	public void loggerThrowing()
	{
		System.out.println("异常通知");
	}

	public void loggerAfter()
	{
		System.out.println("�?终�?�知");
	}

	public Object loggerAround(ProceedingJoinPoint pjp) throws Throwable
	{
		System.out.println("环绕通知-�?");
		Object object = pjp.proceed();
		System.out.println("环绕通知-�?");
		return object;
	}
}
