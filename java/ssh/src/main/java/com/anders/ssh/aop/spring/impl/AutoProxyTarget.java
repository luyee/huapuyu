package com.anders.ssh.aop.spring.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ssh.aop.spring.ProxyTarget;


public class AutoProxyTarget implements ProxyTarget {

	private static final Logger LOG = LoggerFactory.getLogger(AutoProxyTarget.class);

	public void ShowMessage() {
		LOG.debug(this.getClass().getName() + " : ShowMessage");
	}

	@Override
	public void ShowName() {
		LOG.debug(this.getClass().getName() + " : ShowName");
	}

	@Override
	public void printMessage() {
		LOG.debug(this.getClass().getName() + " : printMessage");
	}
}
