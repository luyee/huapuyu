package com.anders.ethan.log.cat.client.log;

import org.apache.log4j.spi.LoggingEvent;

import com.dianping.cat.log4j.CatAppender;

public class MyCatAppender extends CatAppender {

	@Override
	protected void append(LoggingEvent event) {
		//System.out.println(event.getMessage().toString());
		super.append(event);
	}

}
