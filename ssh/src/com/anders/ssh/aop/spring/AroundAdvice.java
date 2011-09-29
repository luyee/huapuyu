package com.anders.ssh.aop.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AroundAdvice implements MethodInterceptor {
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("开始: " + invocation.getMethod().getName());
		Object result = invocation.proceed();
		System.out.println("结束: " + invocation.getMethod().getName());
		return result;
	}
}
