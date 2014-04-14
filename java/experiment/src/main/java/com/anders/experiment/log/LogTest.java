package com.anders.experiment.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {

	private static final Logger LOG = LoggerFactory.getLogger(LogTest.class);

	public void print() {
		LOG.info("此日志不会在日志文件中显示");
	}
}
