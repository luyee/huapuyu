package com.anders.ssh.aop.aspectj.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ssh.aop.aspectj.PersonService;

public class PersonServiceImpl implements PersonService {
	private static final Logger LOG = LoggerFactory.getLogger(PersonServiceImpl.class);

	@Override
	public void get(String name, long i) {
		LOG.debug("get : " + name);

	}

	@Override
	public String save(String name, int i) {
		LOG.debug("save : " + name);
		return "save : " + name;
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
