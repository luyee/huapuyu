package com.anders.ssh.tapestry.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.service.AccountService;

public class AccountAdd {
	@Property
	private Account account = new Account();

	@Inject
	private AccountService accountService;

	public void onSuccess() {
		account.setName("tapestry");
		accountService.save(account);
	}
}
