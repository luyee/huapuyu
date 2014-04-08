package com.anders.ssh.rmi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;
import org.springframework.web.util.NestedServletException;

import com.anders.ssh.log.LogCallPK;

public class MyHttpInvokerServiceExporter extends HttpInvokerServiceExporter {

	private LogCallPK logCallPK;

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RemoteInvocation invocation = readRemoteInvocation(request);
			System.out.println(invocation.getAttribute("callpk"));
			logCallPK.setLogCallPK();
			RemoteInvocationResult result = invokeAndCreateResult(invocation, getProxy());
			writeRemoteInvocationResult(request, response, result);
		} catch (ClassNotFoundException ex) {
			throw new NestedServletException("Class not found during deserialization", ex);
		}
	}

	public LogCallPK getLogCallPK() {
		return logCallPK;
	}

	public void setLogCallPK(LogCallPK logCallPK) {
		this.logCallPK = logCallPK;
	}
}
