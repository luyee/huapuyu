package com.anders.ethan.dubbo.service;

import com.anders.ethan.dubbo.entity.User;

public interface UserService {

	String sayFuck(String name);
	
	String sayHello(User user);
	
	String sayTimeout(String name);
	
	String sayTimeoutJO(String name);

}