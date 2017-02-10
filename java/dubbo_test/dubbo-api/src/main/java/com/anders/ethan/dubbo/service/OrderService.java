package com.anders.ethan.dubbo.service;

import com.anders.ethan.dubbo.entity.Order;

public interface OrderService {

	String sayFuck(String name);
	
	String sayHello(Order order);
	
	String sayTimeout(String name);
	
	String sayTimeoutJO(String name);

}