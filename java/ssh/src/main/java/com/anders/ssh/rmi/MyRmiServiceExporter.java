package com.anders.ssh.rmi;

import java.lang.reflect.InvocationTargetException;

import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.remoting.support.RemoteInvocation;

import com.anders.ssh.log.LogCallPK;

public class MyRmiServiceExporter extends RmiServiceExporter {

	private LogCallPK logCallPK;

	@Override
	protected Object invoke(RemoteInvocation invocation, Object targetObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		System.out.println(invocation.getAttribute("callpk"));
//		logCallPK.setLogCallPK();
		return super.invoke(invocation, targetObject);
	}

	public LogCallPK getLogCallPK() {
		return logCallPK;
	}

	public void setLogCallPK(LogCallPK logCallPK) {
		this.logCallPK = logCallPK;
	}
}
