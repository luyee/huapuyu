package com.anders.ssh.dao.hibernate.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.hibernate.AccountDao;
import com.anders.ssh.dao.hibernate.HibernateDao;

@Component("hibernateAccountDao")
public class AccountDaoImpl extends HibernateDao<Long, Account> implements AccountDao {

	// @Resource
	// public void setSessionFactoryMocker(SessionFactory sessionFactory) {
	// super.setSessionFactory(sessionFactory);
	// }

	@Override
	public HibernateTemplate getTemplate() {
		return getHibernateTemplate();
	}
}
