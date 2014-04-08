package com.anders.ssh.rmi;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.remoting.rmi.RmiInvocationHandler;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.support.RemoteInvocation;

public class MyRmiProxyFactoryBean extends RmiProxyFactoryBean {

	@Override
	protected Object doInvoke(MethodInvocation methodInvocation, RmiInvocationHandler invocationHandler) throws RemoteException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (AopUtils.isToStringMethod(methodInvocation.getMethod())) {
			return "RMI invoker proxy for service URL [" + getServiceUrl() + "]";
		}

		RemoteInvocation ri = createRemoteInvocation(methodInvocation);
		ri.addAttribute("callpk", "123");

		return invocationHandler.invoke(ri);
	}

}
