package com.anders.ssh.common;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mail-test.xml" })
public class MailTest {
	@Resource
	private JavaMailSender javaMailSender;

	@Test
	public void test() {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("huapuyu@sina.com"));
			mimeMessage.setFrom(new InternetAddress("huapuyu@qq.com"));
			mimeMessage.setSubject("SSH Test");
			mimeMessage.setText("Hello World");
			javaMailSender.send(mimeMessage);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
