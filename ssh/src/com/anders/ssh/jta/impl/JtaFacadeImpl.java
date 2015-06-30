package com.anders.ssh.jta.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.jta.HibernateService;
import com.anders.ssh.jta.JtaFacade;
import com.anders.ssh.jta.MyBatisService;

@Service("jtaFacade")
public class JtaFacadeImpl implements JtaFacade {
	@Resource(name = "jtaHibernateService")
	private HibernateService hibernateService;

	@Resource(name = "jtaMyBatisService")
	private MyBatisService myBatisService;

	@Override
	public void save(Account account) {
		myBatisService.save(account);
		hibernateService.save(account);
	}
}
