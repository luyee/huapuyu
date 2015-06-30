package com.anders.ssh.rmi;

import java.rmi.server.RemoteServer;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class SecurityInterceptor implements MethodInterceptor {

	private Set<String> allowed;

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		String clientHost = RemoteServer.getClientHost();
		if (allowed != null && allowed.contains(clientHost)) {
			return methodInvocation.proceed();
		}
		else {
			throw new SecurityException("无权限访问！");
		}
	}

	public Set<String> getAllowed() {
		return allowed;
	}

	public void setAllowed(Set<String> allowed) {
		this.allowed = allowed;
	}
}
