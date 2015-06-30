package com.anders.ssh.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.service.AccountService;
import com.anders.ssh.service.SystemService;

@Service("systemService")
public class SystemServiceImpl implements SystemService {

	@Resource(name = "accountService")
	private AccountService accountService;

	@Override
	public void test(Account account) {
		accountService.save(account);
		accountService.getById(account.getId());
		// account.setName("*********************************************************");
		accountService.update(account);
		// accountService.deleteById(account.getId());

	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
}
