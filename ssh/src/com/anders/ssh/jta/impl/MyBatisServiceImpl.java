package com.anders.ssh.jta.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.dao.mybatis.DataDao;
import com.anders.ssh.jta.MyBatisService;

@Service("jtaMyBatisService")
public class MyBatisServiceImpl implements MyBatisService {
	@Resource(name = "mybatisDataDao")
	private DataDao dataDao;

	@Override
	public void save(Data data) {
		dataDao.save(data);
	}
}
