package com.anders.ethan.dubbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.dubbo.rpc.RpcContext;
import com.anders.ethan.dubbo.entity.User;
import com.anders.ethan.dubbo.service.DemoService;

public class DemoServiceImpl implements DemoService {

	public String sayHello(String name) {
		System.out.println("["
				+ new SimpleDateFormat("HH:mm:ss").format(new Date())
				+ "] fuck " + name + ", request from consumer: "
				+ RpcContext.getContext().getRemoteAddress());
		return "fuck " + name + ", response form provider: "
				+ RpcContext.getContext().getLocalAddress();
	}

	public String sayHello(User user) {
		System.out.println("["
				+ new SimpleDateFormat("HH:mm:ss").format(new Date())
				+ "] Hello " + user.getName() + ", request from consumer: "
				+ RpcContext.getContext().getRemoteAddress());
		return "Hello " + user.getId() + ":" + user.getName()
				+ ", response form provider: "
				+ RpcContext.getContext().getLocalAddress();
	}

}