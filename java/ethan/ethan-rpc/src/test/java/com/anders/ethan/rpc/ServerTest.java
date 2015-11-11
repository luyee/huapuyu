package com.anders.ethan.rpc;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerTest {

	@Test
	public void testServer() {
		new ClassPathXmlApplicationContext("spring-server.xml");
	}
}