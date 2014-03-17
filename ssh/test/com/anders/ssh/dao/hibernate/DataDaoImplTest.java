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
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" }, inheritLocations = true)
public class DataDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {
	// @Autowired
	// @Qualifier("jdbcAccountDao")
	@Resource(name = "hibernateAccountDao")
	private AccountDao accountDao;

	@Rollback(false)
	public void testDataAdd() {
		Account account = new Account();
		account.setId(1L);
		account.setName("zhuzhen");
		account.setEnable(true);
		accountDao.save(account);

		List<Account> dataList = accountDao.getAll();
		Assert.assertEquals(1, dataList.size());

		accountDao.delete(account);
	}

	@Test
	@Rollback(false)
	public void testDataSaveOrUpdate() {
		Account account = new Account();
		account.setId(1L);
		account.setName("zhuzhen1");
		account.setEnable(true);

		accountDao.saveOrUpdate(account);
	}

	@Test
	@Rollback(false)
	public void testDataSaveOrUpdate1() {
		Account account = new Account();
		account.setId(2L);
		account.setName("zhuzhen2");
		account.setEnable(true);
		accountDao.save(account);

		account.setName("zhuzhen3");

		accountDao.saveOrUpdate(account);
	}

	@Rollback(true)
	public void testDataMerge() {
		Account account = new Account();
		account.setId(2L);
		account.setName("zhuzhen2");
		account.setEnable(false);

		// accountDao.merge(account);
	}
}
