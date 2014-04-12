package com.anders.ssh.dao.ibatis.impl;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.ibatis.AccountDao;
import com.anders.ssh.dao.ibatis.IbatisDao;

@Component("ibatisAccountDao")
public class AccountDaoImpl extends IbatisDao<Long, Account> implements AccountDao {
}
