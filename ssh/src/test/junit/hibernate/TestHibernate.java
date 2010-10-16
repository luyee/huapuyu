package test.junit.hibernate;

import javax.annotation.Resource;

import model.test.Tb_user;
import model.xml.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.hibernate.UserDao;
import dao.ibatis.Tb_userDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test/junit/hibernate/spring.xml" })
public class TestHibernate
{
	@Resource
	private UserDao userDao;

	@Resource
	private Tb_userDao tb_userDao;

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

	@Test
	public void testUserAddIbatis()
	{
		Tb_user user = new Tb_user();
		user.setName("ibatis");
		user.setPwd("123");
		tb_userDao.save(user);
	}
}
