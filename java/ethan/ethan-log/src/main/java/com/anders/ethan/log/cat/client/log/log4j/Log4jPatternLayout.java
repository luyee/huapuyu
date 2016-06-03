package com.anders.ethan.log.cat.client.log.log4j;

import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

public class Log4jPatternLayout extends PatternLayout {

	@Override
	public String format(LoggingEvent event) {
		String message = super.format(event);

		if (event.getProperties() != null && event.getProperties().size() > 0) {
			StringBuffer sb = new StringBuffer(message);
			Iterator<Entry<String, Object>> entryKeyIterator = event
					.getProperties().entrySet().iterator();
			while (entryKeyIterator.hasNext()) {
				Entry<String, Object> e = entryKeyIterator.next();
				sb.append(e.getKey());
				sb.append(":");
				sb.append(e.getValue());
			}
			return sb.toString();
		} else {
			return message;
		}
	}
}
