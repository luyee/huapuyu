package com.anders.ssh.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-common-test.xml" })
public class MyNamespaceHandlerSupportTest {
	@Autowired
	private Account account;

	@Test
	public void test() {
		System.out.println(account.getName());
	}
}