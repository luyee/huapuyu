package com.anders.ethan.netty3.server.handler;

import java.util.concurrent.Executor;

import org.jboss.netty.handler.execution.ExecutionHandler;

//提供一个线程池  
public class ServerExecutionHandler extends ExecutionHandler {

	public ServerExecutionHandler(Executor executor) {
		super(executor);
	}
}
