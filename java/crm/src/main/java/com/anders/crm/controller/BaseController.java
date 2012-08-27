package com.anders.crm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Base Controller
 * 
 * @author Anders Zhu
 * 
 */
public abstract class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceBundleMessageSource rbms;

	public ResourceBundleMessageSource getRbms() {
		return rbms;
	}

	public void setRbms(ResourceBundleMessageSource rbms) {
		this.rbms = rbms;
	}
}
