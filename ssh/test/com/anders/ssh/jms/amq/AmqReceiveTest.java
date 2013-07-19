package com.anders.ssh.jms.amq;

import javax.jms.JMSException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class AmqReceiveTest {

	@Test
	public void testReceive() throws JMSException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring-amq.xml");
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		// Destination dest = (Destination) ctx.getBean("queueDest");
		// MapMessage message = (MapMessage) jmsTemplate.receive(dest);
		// System.out.println(jmsTemplate.receive("queueDest"));
		System.out.println(jmsTemplate.receive("topicDest"));
	}
}
