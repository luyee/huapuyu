package com.anders.ssh.log;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogAroundAdvice implements MethodInterceptor {
	private final static Log log = LogFactory.getLog(LogAroundAdvice.class);

	private CallPK callPK;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.error(callPK.getCallPK() + ": " + invocation.getMethod().getName());
		Object result = invocation.proceed();
		log.error(callPK.getCallPK() + ": " + invocation.getMethod().getName());
		return result;
	}

	public CallPK getCallPK() {
		return callPK;
	}

	public void setCallPK(CallPK callPK) {
		this.callPK = callPK;
	}

}
