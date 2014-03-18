package com.anders.ssh.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;

/**
 * 特别注意，如果继承AbstractTransactionalJUnit4SpringContextTests，数据库操作会自动回滚，如果不想回滚，加上@Rollback(false)
 * 
 * @author Anders
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml" }, inheritLocations = true)
public class AccountDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "hibernateAccountDao")
	private AccountDao accountDao;

	@Test
	@Rollback(true)
	public void testSave() {
		Account account = new Account();
		account.setName("zhuzhen");
		account.setEnable(true);
		accountDao.save(account);

		List<Account> list = accountDao.getAll();

		Assert.assertEquals(1, list.size());
		Assert.assertEquals("zhuzhen", list.get(0).getName());
	}

	@Test
	@Rollback(true)
	public void testSaveOrUpdate() {
		Account account = new Account();
		account.setName("guolili");
		account.setEnable(true);
		accountDao.saveOrUpdate(account);

		List<Account> list = accountDao.getAll();

		Assert.assertEquals(1, list.size());
		Assert.assertEquals("guolili", list.get(0).getName());
	}
}
