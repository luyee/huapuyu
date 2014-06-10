package com.vip.mybatis.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vip.mybatis.bo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml" }, inheritLocations = true)
public class UserServiceTest /* extends AbstractTransactionalJUnit4SpringContextTests */{
	@Resource(name = "userService")
	private UserService userService;

	@Test
	// @Rollback(true)
	public void testCRUD() {
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

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 1);
		map.put("name", "zhuzhen1");
		userService.update(map);

		map = new HashMap<String, Object>();
		map.put("id", 101);
		map.put("name", "guolili101");
		userService.update(map);

		map = new HashMap<String, Object>();
		map.put("id", 201);
		map.put("name", "zhulili201");
		userService.update(map);

	}

	@Test
	public void testDelete() {

	}
}
