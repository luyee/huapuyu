package com.banboo.maifang.test.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.banboo.maifang.dao.UserDao;
import com.banboo.maifang.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class UserDaoTest
{
	
	private UserDao userDao;

	@Test
	public void testDataAdd()
	{
		List<User> dataList = userDao.getAll();
		Assert.assertSame(dataList, dataList);
	}
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
