package com.anders.ssh.jms.amq;

import java.io.Serializable;

import javax.jms.Destination;

public interface JmsSender {

	void send(final Destination destination, final Serializable message);

	void send(final String destinationName, final Serializable message);
}
