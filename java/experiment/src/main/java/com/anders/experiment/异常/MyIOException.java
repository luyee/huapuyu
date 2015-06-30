package com.anders.experiment.异常;

import java.io.IOException;

import org.springframework.core.NestedExceptionUtils;

public class MyIOException extends IOException {

	private static final long serialVersionUID = 6241410192795243428L;

	public MyIOException() {
	}

	public MyIOException(String msg) {
		super(msg);
	}

	public MyIOException(String msg, Throwable cause) {
		super(msg);
		initCause(cause);
	}

	public String getMessage() {
		return NestedExceptionUtils.buildMessage(super.getMessage(), getCause());
	}
}
