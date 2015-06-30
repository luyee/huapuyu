package com.anders.ssh.jms.amq;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class CommonJmsSender implements JmsSender {

	private JmsTemplate jmsTemplate;

	@Override
	public void send(final Destination destination, final Serializable message) {

		MessageCreator messageCreator = new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		};
		jmsTemplate.send(destination, messageCreator);
	}

	@Override
	public void send(final String destinationName, final Serializable message) {

		MessageCreator messageCreator = new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(message);
			}
		};
		jmsTemplate.send(destinationName, messageCreator);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
