package com.anders.ssh.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.GenericDao;
import com.anders.ssh.dao.mybatis.AccountDao;
import com.anders.ssh.service.AccountService;

@Service("accountService")
public class AccountServiceImpl extends GenericServiceImpl<Long, Account> implements AccountService {

	@Resource(name = "mybatisAccountDao")
	private AccountDao accountDao;

	@Override
	protected GenericDao<Long, Account> getDao() {
		return accountDao;
	}

	@Override
	public void crud() {
		Account account = new Account();
		account.setId(123L);
		account.setEnable(true);
		account.setName("zhuzhen");
		accountDao.save(account);

		account.setName("guolili");
		accountDao.update(account);
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
}
