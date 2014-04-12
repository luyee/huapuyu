package com.anders.ssh.common;

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
import com.anders.ssh.dao.hibernate.AccountDao;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
@SpringApplicationContext( { "classpath:spring.xml", "classpath:spring-test.xml" })
public class UnitilsTest extends UnitilsJUnit4 {
	@SpringBean("hibernateAccountDao")
	private AccountDao accountDao;

	@Test
	@DataSet("UnitilsTest.xml")
	// @DataSet
	@Transactional(value = TransactionMode.COMMIT, transactionManagerName = "hibernateTxManager")
	// 报错：org.unitils.core.UnitilsException: Make sure that the persistence provider that is used is an instance of UnitilsHibernatePersistenceProvider
	public void test1() {
		Account account = new Account();
		account.setId(123L);
		account.setName("test");
		account.setEnable(true);
		accountDao.save(account);
		// throw new RuntimeException();
		// System.out.println("123");
	}
}
