//package com.anders.ethan.log.dapper.client.log.logback;
//
//import java.io.StringWriter;
//
//import ch.qos.logback.classic.Level;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.classic.spi.IThrowableProxy;
//import ch.qos.logback.core.AppenderBase;
//
//import com.anders.ethan.log.dapper.client.api.TraceGenFactory;
//import com.anders.ethan.log.dapper.client.api.spi.TraceGenerator;
//import com.anders.ethan.log.dapper.client.common.LogSpan;
//
//
//public class MercuryLogbackAppender extends AppenderBase<ILoggingEvent> {
//
//    private static TraceGenerator traceGenerator = TraceGenFactory.getTraceGen();
//
//	@Override
//	protected void append(ILoggingEvent event) {
//		if (event == null)
//			return;
////        if (!mercurySwitch()) return;
//		try {
//			LogSpan span = new LogSpan();
//			//span.setSample(true);
//			span.setTraceId(getTracer().getTraceIdThreadLocal());
//			span.setSpanId(getTracer().getSpanIdThreadLocal());
//            span.setServiceId(getTracer().getServiceIdThreadLocal());
//            span.setServiceType(getTracer().getServiceTypeThreadLocal());
//			span.setLevel(event.getLevel().toString());
//			span.setThreadId(event.getThreadName());
//			span.setTimestamp(System.currentTimeMillis());
////			span.setApp(AppTraceConfig.getLocalConfig().getAppName());
////			span.setHostName(AppTraceConfig.getLocalConfig().getHostName());
////			span.setIp(AppTraceConfig.getLocalConfig().getIp());
//			span.setLogDatail(String.valueOf(event.getFormattedMessage()));
//			span.setEventType(String.valueOf(event.getMessage()));
////			span.setProcessId(AppTraceConfig.getLocalConfig().getProcessId());
////			if (LogManager.getLogManager()!=null &&LogManager.getLogManager().getLogger(event.getLoggerName())!=null) {
////				span.setFile(LogManager.getLogManager().getLogger(event.getLoggerName()).getResourceBundleName());
////			}
//
//			StackTraceElement[] stackTrace = event.getCallerData();
//			if (stackTrace.length == 0) {
//				span.setMethod("unkown");
//				span.setLogIdentity("unkown");
//			} else {
//				StackTraceElement lastElem = stackTrace[0];
//				span.setMethod(lastElem.getClassName() + ":"+ lastElem.getMethodName());
//				span.setLogIdentity(lastElem.getClassName() + ":"+ lastElem.getMethodName()+":"+lastElem.getLineNumber());
//			}
//			if (Level.ERROR.equals(event.getLevel())) {
//
////				logStatManager.incrementErrorCount();
////				logStatManager.setLastErrorTime(System.currentTimeMillis());
//
//				IThrowableProxy error = event.getThrowableProxy();
//				if (error != null) {
//					//String errorClassName = event.getThrowableProxy().getClassName();
//					StringWriter buf = new StringWriter();
//					buf.append(event.getFormattedMessage()).append(" ");
//					if (event.getThrowableProxy()!=null) {
//						buf.append(error.getClassName()).append(": ").append(error.getMessage()).append("\r\n\t");
//					}
//					if (stackTrace.length > 0) {
//						for (int i = 0; i < stackTrace.length; i++) {
//							buf.write("at " + stackTrace[i].toString()+"\r\n\t");
//						}
//						span.setLogDatail(buf.toString());
//					} 
//				}
//			}
//			getTracer().sendLogSpan(span);
//		} catch (Throwable ex) {
//		}
//	}
//
////    private boolean mercurySwitch() {
////        return AppTraceConfig.getLocalConfig().isMercurySwitch();
////    }
//
//    private TraceGenerator getTracer(){
//        return traceGenerator;
//    }
//
//}
//
