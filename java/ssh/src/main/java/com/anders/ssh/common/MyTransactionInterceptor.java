package com.anders.ssh.common;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.interceptor.TransactionInterceptor;

public class MyTransactionInterceptor extends TransactionInterceptor {

	private static final long serialVersionUID = -5150295110627612487L;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("hello");
		return super.invoke(invocation);
	}

}
