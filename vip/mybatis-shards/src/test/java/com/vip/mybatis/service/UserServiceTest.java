package com.vip.mybatis.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vip.mybatis.bo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" }, inheritLocations = true)
public class UserServiceTest /* extends AbstractTransactionalJUnit4SpringContextTests */{
	@Resource(name = "userService")
	private UserService userService;

	@Test
	@Rollback(true)
	public void testSave() {
		User user = new User();
		user.setId(1);
		user.setName("zhuzhen");
		userService.save(user);

		user = new User();
		user.setId(101);
		user.setName("guolili");
		userService.save(user);

		user = new User();
		user.setId(201);
		user.setName("zhulili");
		userService.save(user);
	}

	@Test
	public void test() {

	}
}
