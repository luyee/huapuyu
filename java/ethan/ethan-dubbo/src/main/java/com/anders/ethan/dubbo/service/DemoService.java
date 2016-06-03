package com.anders.ethan.dubbo.service;

import com.anders.ethan.dubbo.entity.User;

public interface DemoService {

	String sayHello(String name);
	
	String sayHello(User user);

}