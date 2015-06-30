package com.anders.crm.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 邮件工具类
 * 
 * @author Anders Zhu
 * 
 */
public class MailUtil {
	/**
	 * 
	 * @param mailSender
	 *            JavaMailSender对象
	 * @param from
	 *            发件邮箱
	 * @param to
	 *            收件邮箱
	 * @param subject
	 *            主题
	 * @param text
	 *            内容
	 * @throws MessagingException
	 */
	public static void sendMail(JavaMailSender mailSender, String from, String to, String subject, String text) throws MessagingException {
		MimeMessage mime = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mime, true, Constant.UNICODE_UTF8);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);
		mailSender.send(mime);
	}
}
