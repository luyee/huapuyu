package com.anders.ethan.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ethan.log.client.common.TraceGenerater;

public class LogbackTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TraceGenerater.class);
	
	public static void main(String[] args) {
		LOGGER.error("zhuzhen");
	}
}
