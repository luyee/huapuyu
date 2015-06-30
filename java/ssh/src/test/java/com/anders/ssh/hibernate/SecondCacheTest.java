package com.anders.ssh.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.unitils.UnitilsJUnit4;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.hibernate.AccountDao;

@RunWith(UnitilsJUnit4TestClassRunner.class)
@SpringApplicationContext("classpath:spring-test.xml")
public class SecondCacheTest extends UnitilsJUnit4 {
	@SpringBean("hibernateAccountDao")
	private AccountDao accountDao;

	@Test
	@DataSet("AccountDaoTest.xml")
	@Transactional(value = TransactionMode.ROLLBACK, transactionManagerName = "transactionManager")
	public void test() {
		accountDao.getHibernateTemplate().setCacheQueries(true);
		accountDao.getHibernateTemplate().execute(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Statistics stat = accountDao.getHibernateTemplate().getSessionFactory().getStatistics();

				// Transaction tx = session.beginTransaction();

				session.setCacheMode(CacheMode.NORMAL);

				List<Account> list = accountDao.getAll();
				System.out.println(list.size());
				list = accountDao.getAll();

				System.out.println(stat.getSecondLevelCacheHitCount());
				System.out.println(stat.getSecondLevelCacheMissCount());
				System.out.println(stat.getSecondLevelCachePutCount());

				// tx.commit();

				return null;
			}
		});
	}
}
