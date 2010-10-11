package test.junit.hibernate;

import javax.annotation.Resource;

import model.xml.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.hibernate.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test/junit/hibernate/spring.xml" })
public class TestHibernate
{
	@Resource
	private UserDao userDao;

	@Test
	public void testUserAdd()
	{
		User user = new User();
		user.setName("zhuzhen");
		user.setPwd("123");
		Byte status = 1;
		user.setStatus(status);
		userDao.save(user);
	}
}
