package com.anders.ethan.rpc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-client.xml")
public class ClientTest {

	// @Autowired
	// private RpcProxy rpcProxy;

	@Test
	public void helloTest() {
		// UserService userService = rpcProxy.create(UserService.class);
		// User user = userService.findById(1L);
		// Assert.assertEquals("zhuzhen", user.getName());
		System.out.println("adfadsas");
	}
}