package com.anders.ssh.aop.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ssh.aop.CustService;

public class CustServiceImpl implements CustService {
	private static final Logger LOG = LoggerFactory.getLogger(CustServiceImpl.class);

	@Override
	public void get(String name, long i) {
		LOG.debug("get : " + name);

	}

	@Override
	public String save(String name, int i) {
		LOG.debug("save : " + name);
		return "保存成功";
	}

	@Override
	public void update(String name) {
		LOG.debug("update : " + name);
	}

	@Override
	public void delete(String name) {
		throw new RuntimeException("错误");
	}
}
