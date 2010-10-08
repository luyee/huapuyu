package test.junit;

import model.xml.User;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import dao.hibernate.UserDao;

@ContextConfiguration(locations = { "classpath:spring-test.xml" })
public class TestDao
{
	private UserDao dao;

	@Test
	public void testUserAdd()
	{
		User user = new User();
		user.setName("zhuzhen");
		user.setPwd("123");
		Byte status = 1;
		user.setStatus(status);
		dao.save(user);
	}
}
