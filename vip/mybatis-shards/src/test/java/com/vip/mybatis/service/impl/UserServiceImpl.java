package com.vip.mybatis.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.vip.mybatis.bo.User;
import com.vip.mybatis.dao.UserDao;
import com.vip.mybatis.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	public void testAddUsers() {
		// 添加
		// 分表1
		User user1 = new User();
		user1.setId(1);
		user1.setName("1");
		userDao.insert(user1);

		User user2 = new User();
		user2.setId(2);
		user2.setName("2");
		userDao.insert(user2);

		// 分表2
		User user3 = new User();
		user3.setId(101);
		user3.setName("101");
		userDao.insert(user3);

		User user4 = new User();
		user4.setId(102);
		user4.setName("102");
		userDao.insert(user4);

		// 分表3
		User user5 = new User();
		user5.setId(201);
		user5.setName("201");
		userDao.insert(user5);

		User user6 = new User();
		user6.setId(202);
		user6.setName("202");
		userDao.insert(user6);

		// 更新
		// 分表1
		user1.setName("zhuzhen");
		userDao.update(user1);

		user2.setName("guolili");
		userDao.update(user2);

		// 分表2
		user3.setName("tom");
		userDao.update(user3);

		user4.setName("kate");
		userDao.update(user4);

		// 分表3
		user5.setName("zhangsan");
		userDao.update(user5);

		user6.setName("lisi");
		userDao.update(user6);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
