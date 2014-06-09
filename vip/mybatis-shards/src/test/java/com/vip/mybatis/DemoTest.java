package com.vip.mybatis;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vip.mybatis.bo.User;
import com.vip.mybatis.service.UserService;

public class DemoTest {

	private ClassPathXmlApplicationContext appCtx;

	@Before
	public void init() {
		// appCtx = new ClassPathXmlApplicationContext(new String[] { "dal-spring.xml", "service-spring.xml" });
		appCtx = new ClassPathXmlApplicationContext(new String[] { "spring-master-slaves.xml" });
	}

	@Test
	public void test_add() {
		UserService userService = appCtx.getBean("userService", UserService.class);
		try {
			User user = new User();
			userService.testAddUsers(123L);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
