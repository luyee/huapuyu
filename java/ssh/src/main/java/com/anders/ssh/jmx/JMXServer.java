package com.anders.ssh.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JMXServer {

	private static final Logger LOG = LoggerFactory.getLogger(JMXServer.class);

	public static void main(String[] args) throws InterruptedException {
		new ClassPathXmlApplicationContext("config/spring-jmx-server.xml");
		LOG.error("server started...");

		Object lock = new Object();
		synchronized (lock) {
			lock.wait();
		}
	}

}
