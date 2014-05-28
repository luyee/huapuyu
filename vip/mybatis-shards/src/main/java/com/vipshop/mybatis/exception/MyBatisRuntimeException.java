package com.vipshop.mybatis.exception;

/**
 * MyBatis运行时异常
 * 
 * @author Anders
 * 
 */
public class MyBatisRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -8517762668549736867L;

	public MyBatisRuntimeException() {
		super();
	}

	public MyBatisRuntimeException(String message) {
		super(message);
	}
}
