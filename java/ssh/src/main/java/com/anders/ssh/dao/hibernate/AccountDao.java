package com.anders.ssh.dao.hibernate;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.GenericDao;

public interface AccountDao extends GenericDao<Long, Account> {
	HibernateTemplate getHibernateTemplate();
}
