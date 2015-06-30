package com.anders.ssh.jms.amq;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-amq-test.xml" })
public class AmqSendTest {

	@Resource
	private JmsTemplate jmsTemplate;

	@Test
	public void testTopicSend() throws JMSException {
		jmsTemplate.send("topicDest", new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("name", "zhuzhen");
				return message;
			}
		});
	}

	@Test
	public void testQueueSend() throws JMSException {
		jmsTemplate.send("queueDest", new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("name", "zhuzhen");
				return message;
			}
		});
	}
}
