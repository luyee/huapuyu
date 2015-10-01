package com.anders.experiment.rpc.provider.impl;

import com.anders.experiment.rpc.api.HelloService;
import com.anders.experiment.rpc.server.RpcService;

@RpcService(HelloService.class) // 指定远程接口
public class HelloServiceImpl implements HelloService {

	@Override
	public String hello(String name) {
		return "Hello! " + name;
	}
}
