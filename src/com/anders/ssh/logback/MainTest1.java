package com.anders.ssh.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class MainTest1 {

	public static void main(String[] args) throws InterruptedException {
		Logger logger = (Logger) LoggerFactory.getLogger(MainTest1.class);
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);
			lc.reset();
			configurator.doConfigure(args[0]);
		}
		catch (JoranException je) {
			je.printStackTrace();
		}
		// After we've called Joran, let's print information about the
		// internal status of logback
		StatusPrinter.print(lc);

		MDC.put("testKey", "testValueFromMDC");
		MDC.put("testKey2", "value2");
		for (int i = 0; i < 10; i++) {
			logger.debug("logging statement " + i);
			Thread.sleep(100);
		}
	}
}
