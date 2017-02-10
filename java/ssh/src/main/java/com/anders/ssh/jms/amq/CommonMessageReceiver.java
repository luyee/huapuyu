package com.anders.ssh.jms.amq;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.listener.SessionAwareMessageListener;

public class CommonMessageReceiver implements SessionAwareMessageListener {

	private static final Logger LOGGER = Logger.getLogger(CommonMessageReceiver.class);

	private List<MessageHandler> messageHandlers;

	public void setMessageHandlers(List<MessageHandler> messageHandlers) {
		this.messageHandlers = messageHandlers;
	}

	@Override
	public void onMessage(Message message, Session session) throws JMSException {

		// if (!(message instanceof ObjectMessage)) {
		// LOGGER.warn("message not processed by consumer " + message.toString());
		// return;
		// }
		//
		// CommonJmsMessage commonJmsMessage = CommonJmsMessage.class.cast(((ObjectMessage) message).getObject());
		// long posId = commonJmsMessage.getPosId();
		//
		// if (null != messageHandlers) {
		// try {
		// for (MessageHandler messageHandler : messageHandlers) {
		// messageHandler.handle(commonJmsMessage);
		// }
		// }
		// catch (Throwable t) {
		// throw new RuntimeException(commonJmsMessage.toString(), t);
		// }
		// }
	}
}
