package com.anders.ssh.ms;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-ms-test.xml" })
public class AccountServiceTest {
	@Resource(name = "accountService")
	private AccountService accountService;

	@Test
	public void testSave() {
		// Account account = new Account();
		// account.setName("zhuzhen");
		// account.setEnable(true);
		// accountService.save(account);

		List<Account> list = accountService.getAll();

		Assert.assertEquals(0, list.size());
		Assert.assertEquals("zhuzhen", list.get(0).getName());
	}

}
