package com.anders.crm.service;

import java.util.Map;

public interface MailService {

	public final static String EMAIL_TO = "email_to";
	public final static String EMAIL_SUBJECT = "subject";

	void getPassword(Map<String, Object> emailParams);

}
