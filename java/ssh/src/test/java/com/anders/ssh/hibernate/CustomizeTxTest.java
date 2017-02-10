package com.anders.ssh.hibernate;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-tx-test.xml" })
public class CustomizeTxTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "accountService")
	private AccountService accountService;

	@Test
	@Rollback(true)
	public void test1() throws Throwable {
		Account account = new Account();
		account.setName("zhubaobao");
		accountService.save(account);
		accountService.delete(account);
	}

}