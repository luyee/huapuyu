package com.anders.ssh.hibernate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.service.AccountService;

/**
 * 测试Hibernate乐观锁
 * 
 * @author Anders Zhu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class OptimisticLockingTest {
	@Autowired
	private AccountService accountService;

	@Before
	public void before() {
		accountService.deleteById(1L);
		accountService.deleteById(2L);
	}

	@After
	public void after() {
		accountService.deleteById(1L);
		accountService.deleteById(2L);
	}

	/**
	 * 出现乐观锁
	 * 
	 * Object of class [com.anders.ssh.bo.xml.Data] with identifier [1]: optimistic locking failed; nested exception is org.hibernate.StaleObjectStateException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect): [com.anders.ssh.bo.xml.Data#1]
	 * 
	 * Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect): [com.anders.ssh.bo.xml.Data#1]
	 */
	@Test(expected = HibernateOptimisticLockingFailureException.class)
	public void test1() {
		Account account = new Account();
		account.setId(1L);
		account.setName("zhuzhen");
		account.setEnable(true);
		accountService.save(account);

		Account data1 = accountService.getById(1L);
		Account data2 = accountService.getById(1L);

		data1.setName("zhuzhen1");
		accountService.update(data1);

		data2.setName("zhuzhen2");
		accountService.update(data2);
	}

	/**
	 * 不出现乐观锁
	 */
	public void test2() {
		Account account = new Account();
		account.setId(1L);
		account.setName("zhuzhen");
		account.setEnable(true);
		accountService.save(account);

		Account data1 = accountService.getById(1L);
		data1.setName("zhuzhen1");
		accountService.update(data1);

		Account data2 = accountService.getById(1L);
		data2.setName("zhuzhen2");
		accountService.update(data2);
	}
}
