/**
 *
 */
package com.anders.ethan.log.client.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ethan.log.client.api.spi.ITraceFactory;
import com.anders.ethan.log.client.api.spi.TraceGenerator;
import com.anders.ethan.log.client.classic.LocalAsySendHolder;
import com.anders.ethan.log.client.classic.LocalAsySendImpl;
import com.anders.ethan.log.common.Annotation;
import com.anders.ethan.log.common.BinaryAnnotation;
import com.anders.ethan.log.common.Endpoint;
import com.anders.ethan.log.common.Span;
import com.anders.ethan.log.common.SpanType;


/**
 * @author mandy
 */
public class TraceGenerater implements TraceGenerator, ITraceFactory {

    private static Logger logger = LoggerFactory.getLogger(TraceGenerater.class);


    private ThreadLocal<Span> spanThreadLocal = new ThreadLocal<Span>();

    private GenerateTraceId generateTraceId = new DefaultGenerateTraceId();

    private LocalAsySendImpl asySendService = LocalAsySendHolder.getInstance();

    private Sample sampler = new SampleImpl();

    private TraceGenerater() {

    }

    public static boolean isTraceEnable() {
        return true;
    }

    @Override
    public TraceGenerator getTraceGen() {
        return getTracer();
    }

    private static class TraceHolder {
        static TraceGenerater instance = new TraceGenerater();
    }

    public static TraceGenerater getTracer() {
        return TraceHolder.instance;
    }

    @Override
    public void removeParentSpan() {
        spanThreadLocal.remove();
    }

    @Override
    public void sendLogSpan(LogSpan logSpan) {
        asySendService.asySendLogSpan(logSpan);
    }

//    @Override
//    public void sendMetricSpan(MetricSpan metricSpan) {
//        asySendService.asySendMetricSpan(metricSpan);
//    }

    @Override
    public Span getParentSpan() {
        return spanThreadLocal.get();
    }

    @Override
    public void setParentSpan(Span span) {
        spanThreadLocal.set(span);
    }


    public void start() throws Exception {
        asySendService.start();
    }

    @Override
    public String getTraceIdThreadLocal() {
        Span span = spanThreadLocal.get();
        if (span != null) {
            return span.getTraceId();
        }
        return null;
    }

    @Override
    public String getSpanIdThreadLocal() {
        Span span = spanThreadLocal.get();
        if (span != null) {
            return span.getId();
        }
        return null;
    }

    @Override
    public String getServiceIdThreadLocal() {
        Span span = spanThreadLocal.get();
        if (span != null) {
            return span.getServiceId();
        }
        return null;
    }

    @Override
    public SpanType getServiceTypeThreadLocal() {
        Span span = spanThreadLocal.get();
        if (span != null) {
            return span.getSpanType();
        }
        return null;
    }


    public static void startTraceWork() {
        try {
            getTracer().start();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void addBinaryAnnotation(BinaryAnnotation b) {
        Span span = spanThreadLocal.get();
        if (span != null) {
            span.addBinaryAnnotation(b);
        }
    }

    //构建rootSpan,是否采样
    @Override
    public Span newSpan(String appName, String spanName, Endpoint endpoint, String serviceId) {
        boolean s = isSample();
        Span span = new Span();
//        span.setApp(appName);
        span.setTraceId(s ? genTracerId() : null);
        span.setId(s ? genSpanId() : null);
        span.setSpanName(spanName);
        span.setServiceId(serviceId);
        span.setSample(s);
//        span.setHost(endpoint.getHost());
//        span.setPort(String.valueOf(endpoint.getPort()));
        return span;
    }

    //构建Span，参数通过上游接口传递过来
    @Override
    public Span genSpan(String appName, String traceId, String pid, String id, String spanName, boolean isSample, String serviceId, Endpoint endpoint) {
        Span span = new Span();
//        span.setApp(appName);
        span.setId(id);
        span.setParentId(pid);
        span.setSpanName(spanName);
        span.setSample(isSample);
        span.setTraceId(traceId);
        span.setServiceId(serviceId);
//        span.setHost(endpoint.getHost());
//        span.setPort(String.valueOf(endpoint.getPort()));
        return span;
    }
    //构建personal annotation
    @Override
    public void simpleRecord(Endpoint endpoint, long end,long duration,String value) {
        Annotation annotation = new Annotation();
        annotation.setValue(value);
        annotation.setTimestamp(end-duration);
        annotation.setHost(endpoint);
        Span span = spanThreadLocal.get();
        if (span != null) {
        	 span.addAnnotation(annotation);
        }
       
    }

    //构建cs annotation
    @Override
    public void clientSendRecord(Span span, Endpoint endpoint, long start) {
        Annotation annotation = new Annotation();
        annotation.setValue(Annotation.CLIENT_SEND);
        annotation.setTimestamp(start);
        annotation.setHost(endpoint);
        span.addAnnotation(annotation);
    }

    //构建cr annotation
    @Override
    public void clientReceiveRecord(Span span, Endpoint endpoint, long end, long duration) {
        Annotation annotation = new Annotation();
        annotation.setValue(Annotation.CLIENT_RECEIVE);
        annotation.setHost(endpoint);
        annotation.setTimestamp(end);
        annotation.setDuration(duration);
        span.addAnnotation(annotation);
        asySendService.asySend(span);
    }

    //构建sr annotation
    @Override
    public void serverReceiveRecord(Span span, Endpoint endpoint, long start) {
        Annotation annotation = new Annotation();
        annotation.setValue(Annotation.SERVER_RECEIVE);
        annotation.setHost(endpoint);
        annotation.setTimestamp(start);
        span.addAnnotation(annotation);
        spanThreadLocal.set(span);
    }

    //构建 ss annotation
    @Override
    public void serverSendRecord(Span span, Endpoint endpoint, long end, int duration) {
        Annotation annotation = new Annotation();
        annotation.setTimestamp(end);
        annotation.setHost(endpoint);
        annotation.setValue(Annotation.SERVER_SEND);
        annotation.setDuration(Long.valueOf(duration));
        span.addAnnotation(annotation);
        asySendService.asySend(span);
    }


    @Override
    public Span newSpan(String spanName) {
        boolean s = isSample();
        Span span = new Span();
        span.setTraceId(s ? genTracerId() : null);
        span.setId(s ? genSpanId() : null);
        span.setSpanName(spanName);
        span.setSample(s);
        return span;
    }

    @Override
    public Endpoint newEndPoint() {
        return new Endpoint();
    }

    @Override
    public boolean isSample() {
        return (asySendService != null && asySendService.isReady()) && sampler.isSample();
    }


    @Override
    public String genTracerId() {
        return generateTraceId.generateTraceId();
    }

    @Override
    public String genSpanId() {
        return generateTraceId.generateSpanId();
    }

    @Override
    public String genLogId() {
        return generateTraceId.generateSpanId();
    }

    @Override
    public void setResultCode(Throwable t, Span span) {

    }

    @Override
    public void asyCatchUnExceptionLogSpan(Throwable throwable, String detail) {
        LogSpan span = new LogSpan();
        span.setTraceId(TraceGenerater.getTracer().getTraceIdThreadLocal());
        span.setSpanId(TraceGenerater.getTracer().getSpanIdThreadLocal());
        span.setServiceId(TraceGenerater.getTracer().getServiceIdThreadLocal());
        span.setServiceType(TraceGenerater.getTracer().getServiceTypeThreadLocal());
        span.setLevel("ERROR");
        span.setThreadId(Thread.currentThread().getName());
        span.setTimestamp(System.currentTimeMillis());
//        span.setApp(AppTraceConfig.getLocalConfig().getAppName());
//        span.setHostName(AppTraceConfig.getLocalConfig().getHostName());
//        span.setIp(AppTraceConfig.getLocalConfig().getIp());
        span.setLogDatail(ExceptionUtil.getExceptionDetail(throwable));
        span.setLogIdentity(throwable.getClass().getName());

//        span.setProcessId(AppTraceConfig.getLocalConfig().getProcessId());

        StackTraceElement[] stackTrace = throwable.getStackTrace();
        if (stackTrace.length == 0) {
            span.setMethod("unkown");
        } else {
            StackTraceElement lastElem = stackTrace[0]; // ArrayOutOfBoundException
            span.setMethod(lastElem.getClassName() + ":" + lastElem.getMethodName());
            span.setLogIdentity(lastElem.getClassName() + ":" + lastElem.getMethodName() + ":" + lastElem.getLineNumber());
        }
        asySendService.asySendLogSpan(span);
    }


}
