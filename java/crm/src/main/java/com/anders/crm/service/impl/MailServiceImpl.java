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
		mailMessage.setTo((String) emailParams.get("emailTo"));
		mailMessage.setSubject((String) emailParams.get("subject"));
		String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "email_get_password.vm", emailParams);
		mailMessage.setText(result);
		mailSender.send(mailMessage);
	}

}
