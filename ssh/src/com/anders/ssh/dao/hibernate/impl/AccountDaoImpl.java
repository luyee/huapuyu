package com.anders.ssh.dao.hibernate.impl;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.hibernate.AccountDao;
import com.anders.ssh.dao.hibernate.HibernateDao;

@Component("hibernateAccountDao")
public class AccountDaoImpl extends HibernateDao<Long, Account> implements AccountDao {
}
