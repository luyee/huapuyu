package com.anders.ethan.log.dapper;

import org.junit.Test;

import com.anders.ethan.log.cat.service.api.UserService;
import com.anders.ethan.log.cat.service.impl.UserServiceImpl;

public class AspectjTest {

	@Test
	public void test() {
		UserService userService = new UserServiceImpl();
		userService.getById(1L);
	}

}
