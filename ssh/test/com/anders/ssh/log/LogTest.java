package com.anders.ssh.log;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.hibernate.AccountDao;

/**
 * 特别注意，如果继承AbstractTransactionalJUnit4SpringContextTests，数据库操作会自动回滚，如果不想回滚，加上@Rollback(false)
 * 
 * @author Anders
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
// inheritLocations的属性，默认为 true,表示子类可以继承该设置
@ContextConfiguration(locations = { "classpath:spring-test.xml" }, inheritLocations = true)
public class LogTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "hibernateAccountDao")
	private AccountDao accountDao;
	@Resource
	private LogCallPK logCallPK;

	@Test
	@Rollback(true)
	public void testSave() {
		logCallPK.setLogCallPK(UUID.randomUUID().toString());

		Account account = new Account();
		account.setName("zhuzhen");
		account.setEnable(true);
		accountDao.save(account);
	}
}
