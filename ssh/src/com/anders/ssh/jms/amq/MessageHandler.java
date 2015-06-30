package com.anders.ssh.jms.amq;

import java.io.Serializable;

public interface MessageHandler {

	public String handleMessageType();

	public void handle(Serializable message);
}
