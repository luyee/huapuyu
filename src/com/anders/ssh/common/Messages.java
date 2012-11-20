package com.anders.ssh.common;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

public class Messages {
	private ResourceBundleMessageSource rbms;
	private Locale locale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));

	public String getMessage(String code) {
		return rbms.getMessage(code, null, locale);
	}

	public String getMessage(String code, String defaultMessage) {
		return rbms.getMessage(code, null, defaultMessage, locale);
	}

	public String getMessage(String code, Object[] args) {
		return rbms.getMessage(code, args, locale);
	}

	public String getMessage(String code, Object[] args, String defaultMessage) {
		return rbms.getMessage(code, args, defaultMessage, locale);
	}

	public ResourceBundleMessageSource getRbms() {
		return rbms;
	}

	public void setRbms(ResourceBundleMessageSource rbms) {
		this.rbms = rbms;
	}
}
