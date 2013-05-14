package com.anders.ssh.hibernate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.annotation.User;
import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.dao.hibernate.DataDao;
import com.anders.ssh.dao.hibernate.UserDao;

/**
 * 多个线程操作同一条记录，如果该记录不存在，则insert，否则则update，测试会不会出现两个线程insert两条记录
 * 
 * @author Anders Zhu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class CommonTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private DataDao dataDao;
	@Resource
	private UserDao userDao;

	@Test
	public void 测试一级缓存中save和update是否执行一条insert语句() throws Throwable {
		dataDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Data data = new Data();
				data.setId(1L);
				data.setType(Byte.MIN_VALUE);
				data.setName("zhuzhen");
				data.setEnable(true);
				session.save(data);

				data.setName("guolili");
				session.update(data);

				return null;
			}
		});
	}

	@Test
	public void 测试获取Map结果() {
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

		List<Map<String, Object>> list = userDao.getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("select id as id, name as name from User").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				// return session.createQuery("select new Map(id as id, name as name) from User").list();
			}
		});

		for (Map<String, Object> map : list) {
			System.out.println(map.get("id"));
			System.out.println(map.get("name"));
		}
	}

	@Test
	public void 测试update和merge区别1() {
		userDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Session session1 = session.getSessionFactory().openSession();
				Transaction tran1 = session1.beginTransaction();
				User user1 = new User();
				user1.setName("zhangsan");
				user1.setUserName("zhangsan");
				user1.setPassword("123");
				session1.save(user1);
				tran1.commit();
				session1.close();

				Session session2 = session.getSessionFactory().openSession();
				Transaction tran2 = session2.beginTransaction();
				User user2 = (User) session2.get(User.class, user1.getId());
				user1.setName("lisi");
				session2.merge(user1);
				session2.delete(user2);
				tran2.commit();
				session2.close();
				return null;
			}
		});
	}

	@Test(expected = HibernateSystemException.class)
	public void 测试update和merge区别2() {
		userDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Session session1 = session.getSessionFactory().openSession();
				Transaction tran1 = session1.beginTransaction();
				User user1 = new User();
				user1.setName("zhangsan");
				user1.setUserName("zhangsan");
				user1.setPassword("123");
				session1.save(user1);
				tran1.commit();
				session1.close();

				Session session2 = session.getSessionFactory().openSession();
				Transaction tran2 = session2.beginTransaction();
				User user2 = (User) session2.get(User.class, user1.getId());
				user1.setName("lisi");
				// 如果用update，报错：org.springframework.orm.hibernate3.HibernateSystemException: a different object with the same identifier value was already associated with the session: [com.anders.ssh.model.annotation.User#4]; nested exception is org.hibernate.NonUniqueObjectException: a different object with the same identifier value was already associated with the session: [com.anders.ssh.model.annotation.User#4]
				session2.update(user1);
				// 如果用merge则ok，merge在执行更新之前会将两个标识符相同的对象进行合并，例如我将name设为lisi，merge后name的值变为lisi。
				// session2.merge(user1);
				session2.delete(user2);
				tran2.commit();
				session2.close();
				return null;
			}
		});
	}
}