package com.anders.experiment.异常;

public class MyRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6992837480000315578L;

	public MyRuntimeException() {
	}

	public MyRuntimeException(String msg) {
		super(msg);
	}
}
