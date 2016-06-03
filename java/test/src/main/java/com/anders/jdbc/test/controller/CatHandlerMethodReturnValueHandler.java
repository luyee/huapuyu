package com.anders.jdbc.test.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CatHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println(returnValue);
	}

}
