package com.anders.ssh.hibernate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.service.DataService;

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
	private DataService dataService;

	@Before
	public void before() {
		dataService.deleteById(1L);
		dataService.deleteById(2L);
	}

	@After
	public void after() {
		dataService.deleteById(1L);
		dataService.deleteById(2L);
	}

	/**
	 * 出现乐观锁
	 */
	@Test
	public void test1() {
		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		dataService.save(data);

		Data data1 = dataService.getById(1L);
		Data data2 = dataService.getById(1L);

		data1.setName("zhuzhen1");
		dataService.update(data1);

		data2.setName("zhuzhen2");
		try {
			dataService.update(data2);
			Assert.fail();
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			// Object of class [com.anders.ssh.bo.xml.Data] with identifier [1]: optimistic locking failed; nested exception is org.hibernate.StaleObjectStateException: Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect): [com.anders.ssh.bo.xml.Data#1]
			System.out.println(ex.getMessage());
			Assert.assertTrue(true);
		}
	}

	/**
	 * 不出现乐观锁
	 */
	@Test
	public void test2() {
		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		dataService.save(data);

		Data data1 = dataService.getById(1L);
		data1.setName("zhuzhen1");
		dataService.update(data1);

		Data data2 = dataService.getById(1L);
		data2.setName("zhuzhen2");
		dataService.update(data2);
	}
}
