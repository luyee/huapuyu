package com.anders.ssh.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis-test.xml" }, inheritLocations = true)
public class AccountServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "accountService")
	private AccountService accountService;

	@Test
	@Rollback(false)
	public void testCrud() {
		accountService.crud();
	}
}
