package com.anders.ssh.dao.mybatis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.anders.ssh.bo.test.Account;

@RunWith(UnitilsJUnit4TestClassRunner.class)
@SpringApplicationContext("classpath:spring-test.xml")
public class AccountDaoUnitilsTest extends UnitilsJUnit4 {
	@SpringBean("mybatisAccountDao")
	private AccountDao accountDao;

	@Test
	@DataSet("AccountDaoTest.xml")
	@Transactional(value = TransactionMode.ROLLBACK, transactionManagerName = "transactionManager")
	public void testMyBatisCache() {
		accountDao.getById(1L);
		accountDao.getById(1L);
	}

	@Test
	@Transactional(value = TransactionMode.ROLLBACK, transactionManagerName = "transactionManager")
	public void testCrud() {
		Account account = new Account();
		account.setId(123L);
		account.setEnable(true);
		account.setName("zhuzhen");
		accountDao.save(account);

		account.setName("guolili");
		accountDao.update(account);

		Account account2 = accountDao.getById(123L);
		Assert.assertEquals("guolili", account2.getName());
	}
}
