package com.anders.ssh.jta.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.dao.hibernate.DataDao;
import com.anders.ssh.jta.HibernateService;

@Service("jtaHibernateService")
public class HibernateServiceImpl implements HibernateService {
	@Resource(name = "hibernateDataDao")
	private DataDao dataDao;

	@Override
	public void save(Data data) {
		dataDao.save(data);
	}
}
