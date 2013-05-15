package com.anders.ssh.jta.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.jta.HibernateService;
import com.anders.ssh.jta.JtaService;
import com.anders.ssh.jta.MyBatisService;

@Service("jtaService")
public class JtaServiceImpl implements JtaService {
	@Resource(name = "jtaHibernateService")
	private HibernateService hibernateService;

	@Resource(name = "jtaMyBatisService")
	private MyBatisService myBatisService;

	@Override
	public void save(Data data) {
		hibernateService.save(data);
		myBatisService.save(data);
	}
}
