package com.anders.crm.exception;

/**
 * 验证码异常类
 * 
 * @author Anders Zhu
 * 
 */
public class SecurityCodeException extends RuntimeException {

	private static final long serialVersionUID = 3350593786192567665L;

	public SecurityCodeException(String msg) {
		super(msg);
	}
}
