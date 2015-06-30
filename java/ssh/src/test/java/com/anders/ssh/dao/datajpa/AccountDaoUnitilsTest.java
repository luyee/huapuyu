package com.anders.ssh.dao.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.anders.ssh.bo.test.Account;

@RunWith(UnitilsJUnit4TestClassRunner.class)
@SpringApplicationContext("classpath:spring-jpa-test.xml")
public class AccountDaoUnitilsTest extends UnitilsJUnit4 {

	@SpringBean("accountDao")
	private AccountDao accountDao;

	@Test
	@Transactional(transactionManagerName = "jpaTxManager")
	public void testSave() {
		Account account = new Account();
		account.setName("zhuzhen");
		account.setEnable(false);
		accountDao.save(account);

		Assert.assertFalse(accountDao.findByName("zhuzhen").getEnable());
	}
}
