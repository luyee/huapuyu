package com.anders.ssh.dao.hibernate;

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
	@SpringBean("hibernateAccountDao")
	private AccountDao accountDao;

	@Test
	@DataSet("AccountDaoTest.xml")
	@Transactional(value = TransactionMode.ROLLBACK, transactionManagerName = "transactionManager")
	// 报错：org.unitils.core.UnitilsException: Make sure that the persistence provider that is used is
	// an instance of UnitilsHibernatePersistenceProvider
	public void testSaveOrUpdate() {
		Account account = new Account();
		account.setId(1L);
		account.setName("guolili");
		account.setEnable(false);
		accountDao.saveOrUpdate(account);
	}
}
