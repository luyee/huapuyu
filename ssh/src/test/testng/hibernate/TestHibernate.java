package test.testng.hibernate;

import javax.annotation.Resource;

import model.User;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import dao.hibernate.UserDao;

@ContextConfiguration(locations = { "classpath:test/testng/hibernate/spring.xml" })
public class TestHibernate extends AbstractTestNGSpringContextTests
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
