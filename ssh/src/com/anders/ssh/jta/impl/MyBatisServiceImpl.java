package com.anders.ssh.jta.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.hibernate.AccountDao;
import com.anders.ssh.jta.MyBatisService;

@Service("jtaMyBatisService")
public class MyBatisServiceImpl implements MyBatisService {
	@Resource(name = "mybatisAccountDao")
	private AccountDao accountDao;

	@Override
	public void save(Account account) {
		accountDao.save(account);
	}
}
