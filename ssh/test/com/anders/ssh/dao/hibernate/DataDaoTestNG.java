package com.anders.ssh.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.anders.ssh.bo.test.Account;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class DataDaoTestNG extends AbstractTestNGSpringContextTests {
	@Resource(name = "hibernateDataDao")
	private AccountDao accountDao;

	@Test
	public void testUserAdd() {
		Account account = new Account();
		account.setId(1L);
		account.setName("zhuzhen");
		account.setEnable(true);
		accountDao.save(account);

		List<Account> dataList = accountDao.getAll();
		Assert.assertEquals(1, dataList.size());
		Assert.assertEquals("zhuzhen", dataList.get(0).getName());
	}
}
