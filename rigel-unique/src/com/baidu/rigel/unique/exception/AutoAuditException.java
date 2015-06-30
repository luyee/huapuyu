package com.baidu.rigel.unique.exception;

public class AutoAuditException extends RuntimeException {

	private static final long serialVersionUID = -5982442957586129793L;

	public AutoAuditException() {
		super();
	}

	public AutoAuditException(String message, Throwable cause) {
		super(message, cause);
	}

	public AutoAuditException(String message) {
		super(message);
	}

	public AutoAuditException(Throwable cause) {
		super(cause);
	}

}
