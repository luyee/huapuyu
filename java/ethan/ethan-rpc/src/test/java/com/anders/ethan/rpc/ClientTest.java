package com.anders.ethan.rpc;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ethan.rpc.api.entity.User;
import com.anders.ethan.rpc.api.service.UserService;
import com.anders.ethan.rpc.client.RpcProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-client.xml")
public class ClientTest {

	@Autowired
	private RpcProxy rpcProxy;

	@Test
	public void testFindById() {
		// for (int i = 0; i < 100000; i++) {
		UserService userService = rpcProxy.create(UserService.class);

		User user = userService.findById(1L);
		Assert.assertEquals("朱振", user.getName());
		// }
	}

	@Test
	public void testFind() {
		UserService userService = rpcProxy.create(UserService.class);
		List<User> users = userService.find(new User());
		Assert.assertEquals(1000000, users.size());
	}
}