package com.anders.crm.facade;

import java.util.Map;

import com.anders.crm.utils.MailType;

public interface MailFacade {
	// public final static String EMAIL_TO = "email_to";
	// public final static String EMAIL_SUBJECT = "subject";

	public final static String USER = "user";

	/**
	 * 发送邮件
	 * 
	 * @param mailType
	 *            邮件类型
	 * @param from
	 *            发件信箱
	 * @param to
	 *            收件信箱
	 * @param subject
	 *            主题
	 * @param emailParams
	 *            邮件参数
	 */
	void sendMail(MailType mailType, String locale, String from, String to, String subject, Map<String, Object> emailParams);
}
