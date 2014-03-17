package com.anders.ssh.dao.jpa;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class DataDaoTest {
	// TODO Anders Zhu : 如果dao类上有事务注解(@Transactional)，会报如下错误（原因可能是没有用接口注入）：
	// [2011-09-06 23:06:03] [org.springframework.test.context.TestContextManager] ERROR : Caught exception while allowing TestExecutionListener [org.springframework.test.context.support.DependencyInjectionTestExecutionListener@8fa0d1] to prepare test instance [com.anders.ssh.dao.hibernate.DataDaoTest@18706f6] (TestContextManager.java:324)
	// org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'com.anders.ssh.dao.hibernate.DataDaoTest': Injection of resource dependencies failed; nested exception is org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 'jpaDataDao' must be of type [com.anders.ssh.dao.jpa.DataDao], but was actually of type [$Proxy22]
	// @Autowired
	// @Qualifier("jdbcAccountDao")
	@Resource(name = "jpaAccountDao")
	private AccountDao accountDao;

	@Test
	public void testDataAdd() {
		Account account = new Account();
		account.setId(1L);
		account.setName("zhuzhen");
		account.setEnable(true);
		accountDao.save(account);

		List<Account> dataList = accountDao.getAll();
		Assert.assertEquals(1, dataList.size());
		Assert.assertEquals("zhuzhen", dataList.get(0).getName());
	}
}
