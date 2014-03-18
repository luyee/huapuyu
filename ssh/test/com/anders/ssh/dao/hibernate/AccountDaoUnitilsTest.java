package com.anders.ssh.dao.hibernate;

import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.Transactional;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.anders.ssh.bo.test.Account;

@SpringApplicationContext("classpath:spring-test.xml")
public class AccountDaoUnitilsTest extends UnitilsJUnit4 {
	@SpringBean("hibernateAccountDao")
	private AccountDao accountDao;

	@Test
	@DataSet("AccountDaoTest.xml")
	@Transactional(transactionManagerName = "hibernateTxManager")
	public void testSaveOrUpdate() {
		Account account = new Account();
		account.setId(1L);
		account.setName("guolili");
		account.setEnable(false);
		accountDao.saveOrUpdate(account);
	}
}
