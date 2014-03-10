package com.anders.experiment.log;

import org.apache.log4j.Logger;

public class LogTest {

	private static final Logger logger = Logger.getLogger(LogTest.class);

	public void print() {
		logger.info("此日志不会在日志文件中显示");
	}
}
