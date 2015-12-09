package com.anders.ethan.sharding.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" }, inheritLocations = true)
public class UserServiceTest {
	
	@Resource
	private UserService userService;

	@Test
	public void testInsert() {
		userService.insert(1L);
	}
	
	@Test
	public void testFindById() {
		userService.findById(1L);
	}
}
