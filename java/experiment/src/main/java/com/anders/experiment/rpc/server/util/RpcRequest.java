package com.anders.experiment.rpc.server.util;

public class RpcRequest {

	private String requestId;
	private String className;
	private String methodName;
	private Class<?>[] parameterTypes;
	private Object[] parameters;

	public String getMethodName() {
		return methodName;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public String getClassName() {
		return className;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

}
