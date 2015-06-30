package com.anders.ssh.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis-test.xml" }, inheritLocations = true)
public class SystemServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "systemService")
	private SystemService systemService;

	@Test
	@Rollback(false)
	public void test() {
		Account account = new Account();
		account.setId(123L);
		account.setEnable(true);
		account.setName("zhuzhen");
		systemService.test(account);
	}
}
