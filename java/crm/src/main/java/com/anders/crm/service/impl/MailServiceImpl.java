package com.anders.crm.service.impl;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.anders.crm.service.MailService;

//@Service("mailService")
@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private SimpleMailMessage mailMessage;
	@Autowired
	private VelocityEngine velocityEngine;

	@Override
	public void getPassword(Map<String, Object> emailParams) {
		// TODO Anders Zhu ：添加异常处理
		// org.springframework.mail.MailSendException: Failed messages: com.sun.mail.smtp.SMTPSendFailedException: 553 From address not verified - see http://help.yahoo.com/l/us/yahoo/mail/original/manage/sendfrom-07.html|; message exceptions (1) are:|Failed message 1: com.sun.mail.smtp.SMTPSendFailedException: 553 From address not verified - see http://help.yahoo.com/l/us/yahoo/mail/original/manage/sendfrom-07.html|
		// at org.springframework.mail.javamail.JavaMailSenderImpl.doSend(JavaMailSenderImpl.java:440)
		// at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:306)
		// at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:296)
		// at com.anders.crm.service.impl.MailServiceImpl.getPassword(MailServiceImpl.java:32)
		mailMessage.setTo((String) emailParams.get(MailService.EMAIL_TO));
		mailMessage.setSubject((String) emailParams.get(MailService.EMAIL_SUBJECT));
		String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "email_get_password.vm", emailParams);
		mailMessage.setText(result);
		mailSender.send(mailMessage);
	}

}
