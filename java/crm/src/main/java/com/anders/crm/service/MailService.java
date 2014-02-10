package com.anders.crm.service;

import java.util.Map;

public interface MailService {

	// public final static String EMAIL_TO = "email_to";
	// public final static String EMAIL_SUBJECT = "subject";

	public final static String PARAM_PWD = "password";

	/**
	 * 获取密码
	 */
	void getPassword(String from, String to, String subject, Map<String, Object> emailParams);

	/**
	 * 注册成功通知
	 */
	void registerSuccess(String from, String to, String subject, Map<String, Object> emailParams);
}
