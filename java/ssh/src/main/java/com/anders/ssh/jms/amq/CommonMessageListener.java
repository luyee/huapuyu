package com.anders.ssh.jms.amq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.listener.SessionAwareMessageListener;

//PropertyAccessException 1: org.springframework.beans.MethodInvocationException: Property 'messageListener' threw exception; nested exception is java.lang.IllegalArgumentException: Message listener needs to be of type [javax.jms.MessageListener] or [org.springframework.jms.listener.SessionAwareMessageListener]
//@Component("messageListener")
//public class CommonMessageListener implements MessageListener {
public class CommonMessageListener implements SessionAwareMessageListener {
	// public void handleMessage(MapMessage message) {
	// System.out.println(message);
	// }

	// @Override
	// public void onMessage(Message message) {
	// System.out.println(message);
	// }

	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		System.out.println(message);
	}
}
