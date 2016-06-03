package com.anders.jdbc.test.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.anders.jdbc.test.entity.Cust;

public class CatHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter != null) {
			if (parameter.getParameterType().isAssignableFrom(Cust.class)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
			throws Exception {
		System.out.println("hhhhhhhhhhhhhhhhhh");
		return null;
	}

}
