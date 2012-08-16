package com.anders.crm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.anders.crm.bo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserDaoTest {
	@Autowired
	private UserDao userDao;

	@Test
	@Transactional()
	@Rollback(false)
	public void test1() {
		User user = new User();
		user.setUsername("zhuzhen8");
		user.setPassword("123");
		user.setName("Zhu Zhen");
		user.setEmail("email8");
		userDao.saveOrUpdate(user);
		user.setId(null);
		user.setName("adfvasdfasdf");
		userDao.saveOrUpdate(user);
	}

	@Test
	@Transactional()
	@Rollback(false)
	public void test2() {
		User user = new User();
		user.setId(16L);
		user.setUsername("zhuzhen9");
		user.setPassword("123");
		user.setName("Zhu Zhen");
		user.setEmail("email9");
		userDao.saveOrUpdate(user);
		user.setName("adfvasdfasdf");
		userDao.saveOrUpdate(user);
	}
}
