package com.anders.ssh.mybatis;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.mybatis.bo.Account;
import com.anders.ssh.mybatis.dao.AccountDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis-for-generator-test.xml" }, inheritLocations = true)
public class AccountDaoUnitilsTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Resource(name = "accountDao")
	private AccountDao accountDao;

	@Test
	@Rollback(false)
	public void testMyBatisCache() {
		Account account = new Account();
		account.setId(1L);
		account.setEnable(true);
		account.setName("zhuzhen");
		accountDao.save(account);

		accountDao.updateBySelective(account);

		account = accountDao.getById(1L);
		Assert.assertEquals("zhuzhen", account.getName());
		Assert.assertNull(account.getEnable());
	}
}
