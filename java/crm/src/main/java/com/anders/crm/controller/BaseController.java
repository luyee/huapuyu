package com.anders.crm.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DelegatingMessageSource;

import com.anders.crm.service.UserService;

/**
 * Base Controller
 * 
 * @author Anders Zhu
 * 
 */
public abstract class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;
	// TODO Anders Zhu : 了解这些注解
	// @Autowired
	// @Qualifier
	// @Value
	// @Required
	// @Resource(type = DelegatingMessageSource.class)
	@Autowired
	private DelegatingMessageSource messageSource;

	// public DelegatingMessageSource getMessageSource() {
	// return messageSource;
	// }

	public String getMessage(String code, HttpServletRequest request) {
		return messageSource.getMessage(code, null, request.getLocale());
	}

	public UserService getUserService() {
		return userService;
	}
}
