package com.anders.ethan.dubbo;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.anders.ethan.dubbo.service.DemoService;

public class RunConsumer {
	@Test
	public void testSave() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-consumer.xml");
		DemoService demoService = (DemoService)context.getBean("demoService");
		System.out.println(demoService.sayHello("hello zhu"));
		System.out.println(demoService.sayHello("hello zhu"));
		System.out.println(demoService.sayHello("hello zhu"));
		System.out.println(demoService.sayHello("hello zhu"));
		System.out.println(demoService.sayHello("hello zhu"));
		
		//RpcContext.getContext()
//		
//		User user = new User();
//		user.setId(2L);
//		user.setName("guolili");
//		System.out.println(demoService.sayHello(user));
		
//		DemoService demo1Service = (DemoService)context.getBean("demo1Service");
//		System.out.println(demo1Service.sayHello("hello zhu"));
		System.in.read();
	}
}
