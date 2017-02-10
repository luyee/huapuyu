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
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class MailTest {
	@Resource
	private JavaMailSender javaMailSender;

	@Test
	public void test1() {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("zhuzhen01@baidu.com"));
			mimeMessage.setFrom(new InternetAddress("huapuyu@yahoo.com.cn"));
			mimeMessage.setSubject("test");
			mimeMessage.setText("hello world");
			javaMailSender.send(mimeMessage);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
