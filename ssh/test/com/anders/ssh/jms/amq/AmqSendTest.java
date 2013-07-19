package com.anders.ssh.jms.amq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class AmqSendTest {

	@Test
	public void testSend() throws JMSException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("config/spring-amq.xml");
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		// Destination dest = (Destination) ctx.getBean("queueDest");

		// jmsTemplate.send("queueDest", new MessageCreator() {
		jmsTemplate.send("topicDest", new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("name", "zhuzhen");
				return message;
			}
		});

	}
}
