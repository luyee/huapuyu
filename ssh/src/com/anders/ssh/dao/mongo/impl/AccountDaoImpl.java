package com.anders.ssh.dao.mongo.impl;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.mongo.AccountDao;
import com.anders.ssh.dao.mongo.MongoDao;

@Component("mongoAccountDao")
public class AccountDaoImpl extends MongoDao<Long, Account> implements AccountDao {
}
