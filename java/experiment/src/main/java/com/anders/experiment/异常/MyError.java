package com.anders.experiment.异常;

public class MyError extends Error {

	private static final long serialVersionUID = -6361374071550656515L;

	public MyError() {
	}

	public MyError(String msg) {
		super(msg);
	}
}
