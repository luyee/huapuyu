package com.anders.crm.service.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.anders.crm.service.MailService;

//@Service("mailService")
@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	// private SimpleMailMessage mailMessage;
	private VelocityEngine velocityEngine;

	@Override
	public void getPassword(String from, String to, String subject, Map<String, Object> emailParams) {
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

		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mime, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "email/get_password.vm", emailParams);
			helper.setText(text);
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		mailSender.send(mime);
	}

	@Override
	public void registerSuccess(String from, String to, String subject, Map<String, Object> emailParams) {
		// mailMessage.setTo(to);
		// mailMessage.setSubject(subject);
		// String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "email/register_success.vm", emailParams);
		// mailMessage.setText(result);
		// mailSender.send(mailMessage);

		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mime, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "email/register_success.vm", emailParams);
			helper.setText(text);
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		mailSender.send(mime);
	}
}
