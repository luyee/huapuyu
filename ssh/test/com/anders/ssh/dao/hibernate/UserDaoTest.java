package com.anders.ssh.dao.hibernate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.annotation.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class UserDaoTest {
	@javax.annotation.Resource(name = "hibernateUserDao")
	private UserDao userDao;

	@Test
	public void testCRUD() {
		User user1 = new User();
		user1.setName("zhangsan");
		user1.setUserName("zhangsan");
		user1.setPassword("123");
		userDao.save(user1);

		User user2 = new User();
		user2.setName("lisi");
		user2.setUserName("lisi");
		user2.setPassword("123");
		userDao.save(user2);

		userDao.getRoleNameListByUserName("zhangsan");

		userDao.delete(user1);
		userDao.delete(user2);
	}
}
