package com.anders.ssh.hibernate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateSystemException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.hibernate.AccountDao;

/**
 * 多个线程操作同一条记录，如果该记录不存在，则insert，否则则update，测试会不会出现两个线程insert两条记录
 * 
 * @author Anders Zhu
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml" })
public class CommonTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "hibernateAccountDao")
	private AccountDao accountDao;

	@Test
	@Rollback(true)
	public void 测试一级缓存中save和update是否执行一条insert语句() throws Throwable {
		accountDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Account account = new Account();
				account.setId(1L);
				account.setName("zhuzhen");
				account.setEnable(true);
				session.save(account);

				account.setName("guolili");
				session.update(account);

				return null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Test
	@Rollback(true)
	public void 测试获取Map结果1() {
		Account account = new Account();
		account.setName("zhangsan");
		accountDao.save(account);

		account = new Account();
		account.setName("lisi");
		accountDao.save(account);

		List<Map<String, Object>> list = accountDao.getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery("select id as id, name as name from Account order by id");
				query.setComment("zhuzhen");
				return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			}
		});

		// Assert.assertEquals("zhangsan", list.get(0).get("name"));
		// Assert.assertEquals("lisi", list.get(1).get("name"));
	}

	@SuppressWarnings("unchecked")
	@Test
	@Rollback(true)
	public void 测试获取Map结果2() {
		Account account = new Account();
		account.setId(1L);
		account.setName("zhangsan");
		accountDao.save(account);

		account = new Account();
		account.setId(2L);
		account.setName("lisi");
		accountDao.save(account);

		List<Map<String, Object>> list = accountDao.getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("select new Map(id as id, name as name) from Account order by id").list();
			}
		});

		Assert.assertEquals("zhangsan", list.get(0).get("name"));
		Assert.assertEquals("lisi", list.get(1).get("name"));
	}

	@Test
	public void 测试update和merge区别1() {
		accountDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Session session1 = session.getSessionFactory().openSession();
				Transaction tran1 = session1.beginTransaction();
				Account account1 = new Account();
				account1.setName("zhangsan");
				session1.save(account1);
				tran1.commit();
				session1.close();

				Session session2 = session.getSessionFactory().openSession();
				Transaction tran2 = session2.beginTransaction();
				Account account2 = (Account) session2.get(Account.class, account1.getId());
				account1.setName("lisi");
				session2.merge(account1);
				session2.delete(account2);
				tran2.commit();
				session2.close();
				return null;
			}
		});
	}

	@Test(expected = HibernateSystemException.class)
	public void 测试update和merge区别2() {
		accountDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Session session1 = session.getSessionFactory().openSession();
				Transaction tran1 = session1.beginTransaction();
				Account account1 = new Account();
				account1.setName("zhangsan");
				session1.save(account1);
				tran1.commit();
				session1.close();

				Session session2 = session.getSessionFactory().openSession();
				Transaction tran2 = session2.beginTransaction();
				Account account2 = (Account) session2.get(Account.class, account1.getId());
				account1.setName("lisi");
				// 如果用update，报错：org.springframework.orm.hibernate3.HibernateSystemException: a
				// different object with the same identifier value was already associated with the
				// session: [com.anders.ssh.model.annotation.User#4]; nested exception is
				// org.hibernate.NonUniqueObjectException: a different object with the same
				// identifier value was already associated with the session:
				// [com.anders.ssh.model.annotation.User#4]
				session2.update(account1);
				// 如果用merge则ok，merge在执行更新之前会将两个标识符相同的对象进行合并，例如我将name设为lisi，merge后name的值变为lisi。
				// session2.merge(account1);
				session2.delete(account2);
				tran2.commit();
				session2.close();
				return null;
			}
		});
	}
}