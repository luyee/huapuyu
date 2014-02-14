package com.anders.crm.facade.impl;

import java.util.Map;

import javax.mail.MessagingException;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.anders.crm.facade.MailFacade;
import com.anders.crm.utils.MailType;
import com.anders.crm.utils.MailUtil;

//@Service("mailFacade")
@Service
public class MailFacadeImpl implements MailFacade {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	// private SimpleMailMessage mailMessage;
	private VelocityEngine velocityEngine;

	@Override
	public void sendMail(MailType mailType, String from, String to, String subject, Map<String, Object> emailParams) {
		// TODO Anders Zhu ：添加异常处理
		// org.springframework.mail.MailSendException: Failed messages: com.sun.mail.smtp.SMTPSendFailedException: 553 From address not verified - see http://help.yahoo.com/l/us/yahoo/mail/original/manage/sendfrom-07.html|; message exceptions (1) are:|Failed message 1: com.sun.mail.smtp.SMTPSendFailedException: 553 From address not verified - see http://help.yahoo.com/l/us/yahoo/mail/original/manage/sendfrom-07.html|
		// at org.springframework.mail.javamail.JavaMailSenderImpl.doSend(JavaMailSenderImpl.java:440)
		// at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:306)
		// at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:296)
		// at com.anders.crm.service.impl.MailServiceImpl.getPassword(MailServiceImpl.java:32)
		// mailMessage.setTo(to);
		// mailMessage.setSubject(subject);
		// String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "email/get_password.vm", emailParams);
		// mailMessage.setText(result);
		// mailSender.send(mailMessage);

		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, String.format("email/%s.vm", mailType.getName()), emailParams);

		try {
			MailUtil.sendMail(mailSender, from, to, subject, text);
		}
		catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
