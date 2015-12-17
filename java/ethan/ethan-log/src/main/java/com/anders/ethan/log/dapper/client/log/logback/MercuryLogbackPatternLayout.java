//package com.anders.ethan.log.dapper.client.log.logback;
//
//import java.util.Iterator;
//import java.util.Map.Entry;
//
//import ch.qos.logback.classic.PatternLayout;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//
//public class MercuryLogbackPatternLayout extends  PatternLayout{
//	
//	public String doLayout(ILoggingEvent event) {
//		String message = super.doLayout(event);
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
//	  }
//	
//	  
//
// }
