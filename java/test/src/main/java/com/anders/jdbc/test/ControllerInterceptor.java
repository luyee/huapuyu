package com.anders.jdbc.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ControllerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//		System.out.println(request.getLocalAddr());
//		System.out.println(request.getLocalPort());
//		System.out.println(request.getRemoteAddr());
//		System.out.println(request.getRemotePort());
//		System.out.println(handler.getClass().getName());
//		System.out.println(modelAndView);
		super.postHandle(request, response, handler, modelAndView);
	}
	
	

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("*******************" + ex);
		super.afterCompletion(request, response, handler, ex);
	}



	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println(request.getLocalAddr());
		System.out.println(request.getLocalPort());
		System.out.println(request.getRemoteAddr());
		System.out.println(request.getRemotePort());
		System.out.println(request.getRequestURI());
		System.out.println(request.getScheme());
		System.out.println(request.getServerName());
		System.out.println(request.getServerPort());
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		System.out.println(handlerMethod.getBean().getClass().getName());
		System.out.println(handlerMethod.getMethod().getName());
		System.out.println(handler.getClass().getName());
		System.out.println(response);
		
		
		System.out.println(request.getScheme() + "//" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI());
		return super.preHandle(request, response, handler);
	}

}
