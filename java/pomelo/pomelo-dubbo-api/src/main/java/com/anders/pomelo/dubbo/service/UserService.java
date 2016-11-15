package com.anders.pomelo.dubbo.service;

import com.anders.pomelo.dubbo.entity.User;

public interface UserService {

	String sayFuck(String name);
	
	String sayHello(User user);
	
	String sayTimeout(String name);
	
	String sayTimeoutJO(String name);

}