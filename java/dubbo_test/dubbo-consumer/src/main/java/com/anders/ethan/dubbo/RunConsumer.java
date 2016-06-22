package com.anders.ethan.dubbo;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.anders.ethan.dubbo.service.UserService;

public class RunConsumer {
	@Test
	public void testSave() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-consumer.xml");
		UserService userService = (UserService) context.getBean("userService");
		// System.out.println(userService.sayFuck("laosan"));
		// System.out.println(userService.sayFuck("laosan"));
		// System.out.println(userService.sayFuck("laosan"));
		// System.out.println(userService.sayFuck("laosan"));
		// System.out.println(userService.sayFuck("laosan"));
		long begin = new Date().getTime();
		System.out.println(userService.sayFuck("laosan"));
		System.out.println(new Date().getTime() - begin);
		
		begin = new Date().getTime();
		System.out.println(userService.sayTimeoutJO("laosan"));
		System.out.println(new Date().getTime() - begin);
		
		begin = new Date().getTime();
		System.out.println(userService.sayFuck("laosan"));
		System.out.println(new Date().getTime() - begin);
		
		begin = new Date().getTime();
		System.out.println(userService.sayFuck("laosan"));
		System.out.println(new Date().getTime() - begin);

		// RpcContext.getContext()
		//
		// User user = new User();
		// user.setId(2L);
		// user.setName("guolili");
		// System.out.println(userService.sayHello(user));

		// DemoService user1Service =
		// (DemoService)context.getBean("user1Service");
		// System.out.println(user1Service.sayHello("hello zhu"));
		System.in.read();
	}
	
	@Test
	public void testCircle() throws IOException, InterruptedException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-consumer.xml");
		UserService userService = (UserService) context.getBean("userService");
		while (true) {
			System.out.println(userService.sayFuck("laosan"));
			
			Thread.sleep(5000);
		}
	}

	@Test
	public void testHighTps() throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-consumer.xml");
		UserService userService = (UserService) context.getBean("userService");

		ExecutorService pool = Executors.newFixedThreadPool(50);
		for (int i = 0; i < 50; i++) {
			pool.execute(new MyThread(userService));
		}

		System.in.read();
	}

	@Test
	public void testCache() throws IOException, InterruptedException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-consumer.xml");
		UserService userService = (UserService) context.getBean("userService");
		for (int i = 0; i < 1005; i++) {
			System.out.println(userService.sayFuck("laosan"));
			System.out.println(userService.sayFuck("laosan"));
			System.out.println(userService.sayFuck("laosan"));
		}
		
		Thread.sleep(10000);
		
		for (int i = 0; i < 1005; i++) {
			System.out.println(userService.sayFuck("laosan"));
			System.out.println(userService.sayFuck("laosan"));
			System.out.println(userService.sayFuck("laosan"));
		}
		
		System.in.read();
	}

	class MyThread extends Thread {

		private UserService userService;

		public MyThread(UserService userService) {
			this.userService = userService;
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " " + userService.sayTimeout("laosan"));
		}
	}
}
