package com.anders.ssh.log4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class Log4jTest {
	private static Log log = LogFactory.getLog(Log4jTest.class);

	@Test
	public void test() {
		log.debug("debug");
		log.info("info");
		log.warn("warn");
		log.error("error");
		log.fatal("fatal");
	}
}
