package com.anders.ssh.quartz;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AutoGc extends QuartzJobBean {

	private static Logger log = Logger.getLogger(AutoGc.class);

	private int timeout;

	private JavaMailSender javaMailSender;

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	private void gc() throws Exception {
		Runtime rt = Runtime.getRuntime();
		long maxMemory = rt.maxMemory();
		long freeMemory = rt.freeMemory();
		log.info("auto gc [ free:" + freeMemory + ", max:" + maxMemory + ", percent:" + (double) freeMemory / maxMemory + " ]");
		System.out.println("auto gc [ free:" + freeMemory + ", max:" + maxMemory + ", percent:" + (double) freeMemory / maxMemory + " ]");
		sendMail();
		if ((double) freeMemory / maxMemory < 0.5)
			rt.gc();
	}

	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			this.gc();
		}
		catch (Exception e) {
			log.error("gc failed");
		}
	}

	public static void main(String[] args) {
		AutoGc gc = new AutoGc();
		try {
			gc.gc();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("finish");
	}

	private void sendMail() {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("zhuzhen01@baidu.com"));
			mimeMessage.setFrom(new InternetAddress("huapuyu@yahoo.com.cn"));
			mimeMessage.setText("hello world");
			javaMailSender.send(mimeMessage);
		}
		catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
}
