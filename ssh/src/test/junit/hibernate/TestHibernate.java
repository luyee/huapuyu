package test.junit.hibernate;

import javax.annotation.Resource;

import model.test.Tb_user;
import model.xml.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.hibernate.UserDao;
import dao.interf.ITb_userDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test/junit/hibernate/spring.xml" })
public class TestHibernate
{
	@Resource
	private UserDao userDao;

	// Caused by: org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 'tb_userDao' must be of type [dao.ibatis.Tb_userDao], but was actually of type [$Proxy10]
	// 如果使用private Tb_userDao tb_userDao;这种方式注入，会报上面的error。必须使用下面接口的注入方式
	@Resource
	private ITb_userDao tb_userDao;

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
