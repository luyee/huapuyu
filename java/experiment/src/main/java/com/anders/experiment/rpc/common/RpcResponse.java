package com.anders.experiment.rpc.common;

public class RpcResponse {

	private String requestId;
	private Throwable error;
	private Object result;

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	public boolean isError() {
		return error != null;
	}

	public Object getResult() {
		return result;
	}

	public Throwable getError() {
		return error;
	}
}
