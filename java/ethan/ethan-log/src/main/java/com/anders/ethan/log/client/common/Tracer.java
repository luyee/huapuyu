package com.anders.ethan.log.client.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anders.ethan.log.common.Annotation;
import com.anders.ethan.log.common.BinaryAnnotation;
import com.anders.ethan.log.common.Endpoint;
import com.anders.ethan.log.common.Span;


public class Tracer  {
	 private static final Logger LOGGER = LoggerFactory.getLogger(Tracer.class);

	    private static Tracer trace = null;

	    private Sample sampler = new SampleImp();
	    private GenerateTraceId generateTraceId = new DefaultGenerateTraceId();
//
//	    private SyncTransfer transfer = null;

	    //传递parentSpan
	    private ThreadLocal<Span> spanThreadLocal = new ThreadLocal<Span>();

//	    TraceService traceService;

	    private Tracer() {
	    }

	    public void removeParentSpan() {
	        spanThreadLocal.remove();
	    }

	    public Span getParentSpan() {
	        return spanThreadLocal.get();
	    }

	    public void setParentSpan(Span span) {
	        spanThreadLocal.set(span);
	    }

	    public Span genSpan(String traceId, String pid, String id, String spanName, boolean sample, String serviceId) {
	        Span span = new Span();
	        span.setId(id);
	        span.setParentId(pid);
	        span.setSpanName(spanName);
	        span.setSample(sample);
	        span.setTraceId(traceId);
	        span.setServiceId(serviceId);
	        return span;
	    }

	    public Span newSpan(String spanName, Endpoint endpoint, String serviceId) {
	        boolean s = isSample();
	        Span span = new Span();
	        span.setTraceId(s ? genTracerId() : null);
	        span.setId(s ? genSpanId() : null);
	        span.setSpanName(spanName);
	        span.setServiceId(serviceId);
	        span.setSample(s);
	        return span;
	    }

	    public Endpoint newEndPoint() {
	        return new Endpoint();
	    }

	    private static class  TraceHolder{
	        static Tracer instance=new Tracer();
	    }
	    public static Tracer getTracer() {
	       return TraceHolder.instance;
	    }

	    public void start() throws Exception {
	        transfer.start();
	    }

	    //启动后台消息发送线程
	    public static void startTraceWork() {
	        try {
	            getTracer().start();
	        } catch (Exception e) {
	            logger.error(e.getMessage());
	        }
	    }


	    public boolean isSample() {
	        return sampler.isSample() && (transfer != null && transfer.isReady());
	    }

	    public void addBinaryAnntation(BinaryAnnotation b) {
	        Span span = spanThreadLocal.get();
	        if (span != null) {
	            span.addBinaryAnnotation(b);
	        }
	    }

	    //构件cs annotation
	    public void clientSendRecord(Span span, Endpoint endpoint, long start) {
	        Annotation annotation = new Annotation();
	        annotation.setValue(Annotation.CLIENT_SEND);
	        annotation.setTimestamp(start);
	        annotation.setHost(endpoint);
	        span.addAnnotation(annotation);
	    }


	    //构件cr annotation
	    public void clientReceiveRecord(Span span, Endpoint endpoint, long end) {
	        Annotation annotation = new Annotation();
	        annotation.setValue(Annotation.CLIENT_RECEIVE);
	        annotation.setHost(endpoint);
	        annotation.setTimestamp(end);
	        span.addAnnotation(annotation);
	        transfer.syncSend(span);
	    }

	    //构件sr annotation
	    public void serverReceiveRecord(Span span, Endpoint endpoint, long start) {
	        Annotation annotation = new Annotation();
	        annotation.setValue(Annotation.SERVER_RECEIVE);
	        annotation.setHost(endpoint);
	        annotation.setTimestamp(start);
	        span.addAnnotation(annotation);
	        spanThreadLocal.set(span);
	    }

	    //构件 ss annotation
	    public void serverSendRecord(Span span, Endpoint endpoint, long end) {
	        Annotation annotation = new Annotation();
	        annotation.setTimestamp(end);
	        annotation.setHost(endpoint);
	        annotation.setValue(Annotation.SERVER_SEND);
	        span.addAnnotation(annotation);
	        transfer.syncSend(span);
	    }

	    public String getServiceId(String name) {
	        String id = null;
	        if (transfer != null) {
	            id = transfer.getServiceId(name);
	        }
	        return id;
	    }

	    public String genTracerId() {
	        return  generateTraceId.generateTraceId();
	    }

	    public String genSpanId() {
	        return generateTraceId.generateSpanId();
	    }

	    public void setTraceService(TraceService traceService) {
	        this.traceService = traceService;
	    }

	    public void setTransfer(SyncTransfer transfer) {
	        this.transfer = transfer;
	    }
}
