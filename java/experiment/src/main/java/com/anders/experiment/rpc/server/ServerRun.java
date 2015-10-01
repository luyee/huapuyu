package com.anders.experiment.rpc.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerRun {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("server.xml");
	}
}
