package com.anders.ethan.log.cat.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.anders.ethan.log.cat.service.api.UserService;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

public class DubboConsumer {
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:dubbo-consumer.xml");
		UserService userService = (UserService) context.getBean("userService");

		Transaction transaction = Cat.newTransaction("MVC",
				"http://127.0.0.1/getById");

		System.out.println(userService.getById(1L));
		System.out.println(userService.getById(1L));
		try {
			System.out.println(userService.getById(null));
			transaction.setStatus(Transaction.SUCCESS);
		} catch (Exception e) {
			transaction.setStatus(e);
			e.printStackTrace();
		} finally {
			transaction.complete();
		}
	}
}
