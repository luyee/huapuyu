package com.anders.ssh.log;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogAroundAdvice implements MethodInterceptor {
	private final static Log log = LogFactory.getLog(LogAroundAdvice.class);

	private LogCallPK logCallPK;

	public Object invoke(MethodInvocation invocation) throws Throwable {
//		log.error(logCallPK.getLogCallPK() + " : " + invocation.getMethod().getName());
		Object result = invocation.proceed();
//		log.error(logCallPK.getLogCallPK() + " : " + invocation.getMethod().getName());
		return result;
	}

	public LogCallPK getLogCallPK() {
		return logCallPK;
	}

	public void setLogCallPK(LogCallPK logCallPK) {
		this.logCallPK = logCallPK;
	}

}
