package com.anders.ethan.dubbo.service;

import com.anders.ethan.dubbo.entity.User;

public class DemoServiceMock implements UserService {

	@Override
	public String sayFuck(String name) {
		return "mock sayFuck";
	}

	@Override
	public String sayHello(User user) {
		return "mock sayHello";
	}

	@Override
	public String sayTimeout(String name) {
		return "mock sayTimeout";
	}

	@Override
	public String sayTimeoutJO(String name) {
		return "mock sayTimeoutJO";
	}

	

}