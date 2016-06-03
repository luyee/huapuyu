package com.anders.ethan.sharding.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-jta.xml" }, inheritLocations = true)
public class UserService4JtaTest {

	@Resource
	private UserService userService;

	public void testInsert() {
		userService.insert(1L);
	}

	@Test
	public void testFindById() {
		userService.findById(1L);
//		userService.findById(1L);
	}
}
