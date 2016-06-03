package com.anders.ethan.log.cat.client.log.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;

public class LogbackLayout extends JsonLayout {

	@Override
	public String doLayout(ILoggingEvent event) {
		String message = super.doLayout(event);
		
		System.out.println("asdfas" + message);
		return message;
//		if (event!=null &&event.getLoggerContextVO()!=null && event.getLoggerContextVO().getPropertyMap() != null && event.getLoggerContextVO().getPropertyMap().size() > 0) {
//			StringBuffer sbBuffer = new StringBuffer(message);
//			Iterator<Entry<String, String>> entryKeyIterator = event.getLoggerContextVO().getPropertyMap().entrySet().iterator();
//			while (entryKeyIterator.hasNext()) {
//				Entry<String, String> e = entryKeyIterator.next();
//				sbBuffer.append(e.getKey());
//				sbBuffer.append(":");
//				sbBuffer.append(e.getValue());
//			}
//			return sbBuffer.toString();
//		} else {
//			return message;
//		}
	}
}
