package com.anders.ssh.jta;

import com.anders.ssh.bo.test.Account;

public interface HibernateService {
	void save(Account account);
}