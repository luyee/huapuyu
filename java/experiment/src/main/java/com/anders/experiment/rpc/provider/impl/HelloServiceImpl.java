package com.anders.experiment.rpc.provider.impl;

import org.springframework.stereotype.Component;

import com.anders.experiment.rpc.api.HelloService;
import com.anders.experiment.rpc.server.RpcService;

@Component
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "Hello! " + name;
	}
}
