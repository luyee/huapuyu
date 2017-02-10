package com.anders.experiment.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTest {

	private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);

	public static void main(String[] args) {
		LOG.info("此日志会在日志文件中显示");
		new LogTest().print();
	}
}
