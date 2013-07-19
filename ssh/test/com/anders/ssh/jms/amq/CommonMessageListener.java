package com.anders.ssh.jms.amq;

import javax.jms.Message;
import javax.jms.MessageListener;

//PropertyAccessException 1: org.springframework.beans.MethodInvocationException: Property 'messageListener' threw exception; nested exception is java.lang.IllegalArgumentException: Message listener needs to be of type [javax.jms.MessageListener] or [org.springframework.jms.listener.SessionAwareMessageListener]
//@Component("messageListener")
public class CommonMessageListener implements MessageListener {
	// public void handleMessage(MapMessage message) {
	// System.out.println(message);
	// }

	@Override
	public void onMessage(Message message) {
		System.out.println(message);
	}
}
