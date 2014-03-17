package com.anders.ssh.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.GenericDao;
import com.anders.ssh.dao.hibernate.AccountDao;
import com.anders.ssh.service.AccountService;

@Service("accountService")
public class AccountServiceImpl extends GenericServiceImpl<Long, Account> implements AccountService {

	@Resource(name = "hibernateAccountDao")
	private AccountDao accountDao;

	@Override
	protected GenericDao<Long, Account> getDao() {
		return accountDao;
	}
}
