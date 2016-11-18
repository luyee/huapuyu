package com.anders.pomelo.dubbo.service;

import com.anders.pomelo.dubbo.entity.Order;

public interface OrderService {

	String sayFuck(String name);
	
	String sayHello(Order order);
	
	String sayTimeout(String name);
	
	String sayTimeoutJO(String name);

}