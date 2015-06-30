package com.anders.ssh.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JMXClient {

	private static final Logger LOG = LoggerFactory.getLogger(JMXClient.class);

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring-jmx-client.xml");
		LOG.error("client started...");
		AndersMBean proxy = (AndersMBean) ctx.getBean("proxy");
		String message = proxy.show();
		LOG.error("show:" + message);
		proxy.setId(10L);
		message = proxy.show();
		LOG.error("show:" + message);

		Object lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
	}

}
