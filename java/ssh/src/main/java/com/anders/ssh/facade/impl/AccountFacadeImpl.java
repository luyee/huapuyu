package com.anders.ssh.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.facade.AccountFacade;
import com.anders.ssh.service.AccountService;

@Component("accountFacade")
public class AccountFacadeImpl implements AccountFacade {

	@Resource(name = "accountService")
	private AccountService accountService;

	@Override
	public void 读写分离() {
		Account account = new Account();
		account.setName("zhuzhen");
		account.setEnable(true);
		accountService.save(account);

		List<Account> list = accountService.getAll();

		Assert.isTrue(list.size() == 0);
	}

}
