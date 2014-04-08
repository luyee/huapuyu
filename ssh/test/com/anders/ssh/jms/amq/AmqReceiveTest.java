package com.anders.ssh.jms.amq;

import javax.annotation.Resource;
import javax.jms.JMSException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-amq-test.xml" })
public class AmqReceiveTest {

	@Resource
	private JmsTemplate jmsTemplate;

	@Test
	public void testTopicReceive() throws JMSException {
		System.out.println(jmsTemplate.receive("topicDest"));
	}

	@Test
	public void testQueueReceive() throws JMSException {
		System.out.println(jmsTemplate.receive("queueDest"));
	}
}
