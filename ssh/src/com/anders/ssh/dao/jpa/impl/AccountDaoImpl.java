package com.anders.ssh.dao.jpa.impl;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.jpa.AccountDao;
import com.anders.ssh.dao.jpa.JpaDao;

@Component("jpaAccountDao")
public class AccountDaoImpl extends JpaDao<Long, Account> implements AccountDao {

}
