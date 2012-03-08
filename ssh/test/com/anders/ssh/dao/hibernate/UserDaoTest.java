package com.anders.ssh.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.model.annotation.User;

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
		List<Map<String, Object>> list = userDao.getMap();
		for (Map<String, Object> map : list) {
			System.out.println(map.get("id"));
			System.out.println(map.get("name"));
		}

		userDao.delete(user1);
		userDao.delete(user2);
	}

	@Test
	public void testUpdateAndMerge() {
		userDao.testUpdateAndMerge();
	}
}
