package com.anders.pomelo.dubbo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.dubbo.rpc.RpcContext;
import com.anders.pomelo.dubbo.entity.User;
import com.anders.pomelo.dubbo.service.UserService;

public class UserServiceImpl implements UserService {

	private AtomicInteger count = new AtomicInteger(0);

	public UserServiceImpl() {
	}

	@Override
	public String sayFuck(String name) {
		System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Fuck " + name + ", request from consumer : " + RpcContext.getContext().getRemoteAddress());
		return "Fuck " + name + ", response from provider : " + RpcContext.getContext().getLocalAddress();
	}

	@Override
	public String sayHello(User user) {
		System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + user.getName() + ", request from consumer : " + RpcContext.getContext().getRemoteAddress());
		return "Hello " + user.getId() + "-" + user.getName() + ", response from provider : " + RpcContext.getContext().getLocalAddress();
	}

	@Override
	public String sayTimeout(String name) {
		System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Timeout " + name + ", request from consumer : " + RpcContext.getContext().getRemoteAddress());

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Timeout " + name + ", response from provider : " + RpcContext.getContext().getLocalAddress();
	}

	@Override
	public String sayTimeoutJO(String name) {
		System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Timeout " + name + ", request from consumer : " + RpcContext.getContext().getRemoteAddress());

		if ((count.getAndIncrement() % 2) == 0) {
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return "Timeout " + name + ", response from provider : " + RpcContext.getContext().getLocalAddress();
	}

}