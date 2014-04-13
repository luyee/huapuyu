package com.anders.ssh.rmi;

import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

public class MyHttpInvokerProxyFactoryBean extends HttpInvokerProxyFactoryBean {

	@Override
	protected RemoteInvocationResult executeRequest(RemoteInvocation invocation) throws Exception {
		invocation.addAttribute("callpk", "123");
		return super.executeRequest(invocation);
	}

}
