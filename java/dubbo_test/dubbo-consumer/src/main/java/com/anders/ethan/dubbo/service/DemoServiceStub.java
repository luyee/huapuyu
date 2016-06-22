package com.anders.ethan.dubbo.service;

import com.anders.ethan.dubbo.entity.User;

public class DemoServiceStub implements UserService {

	private final UserService demoService;

	public DemoServiceStub(UserService demoService) {
		this.demoService = demoService;
	}

	@Override
	public String sayFuck(String name) {
		System.out.println("stub sayFuck");
		return demoService.sayFuck(name);
	}

	@Override
	public String sayHello(User user) {
		System.out.println("stub sayHello");
		return demoService.sayHello(user);
	}

	@Override
	public String sayTimeout(String name) {
		System.out.println("stub sayTimeout");
		return demoService.sayTimeout(name);
	}

	@Override
	public String sayTimeoutJO(String name) {
		System.out.println("stub sayTimeoutJO");
		return demoService.sayTimeout(name);
	}

}