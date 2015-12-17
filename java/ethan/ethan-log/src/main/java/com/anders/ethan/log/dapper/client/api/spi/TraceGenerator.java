package com.anders.ethan.log.dapper.client.api.spi;

import com.anders.ethan.log.dapper.client.common.LogSpan;
import com.anders.ethan.log.dapper.common.BinaryAnnotation;
import com.anders.ethan.log.dapper.common.Endpoint;
import com.anders.ethan.log.dapper.common.Span;
import com.anders.ethan.log.dapper.common.SpanType;

public interface TraceGenerator {
	void removeParentSpan();

	void sendLogSpan(LogSpan logSpan);

	// void sendMetricSpan(MetricSpan metricSpan);

	Span getParentSpan();

	void setParentSpan(Span span);

	void addBinaryAnnotation(BinaryAnnotation b);

	// 构建rootSpan,是否采样
	Span newSpan(String appName, String spanName, Endpoint endpoint,
			String serviceId);

	// 构建Span，参数通过上游接口传递过来
	Span genSpan(String appName, String traceId, String pid, String id,
			String spanName, boolean isSample, String serviceId,
			Endpoint endpoint);

	void simpleRecord(Endpoint endpoint, long end, long duration, String value);

	// 构建cs annotation
	void clientSendRecord(Span span, Endpoint endpoint, long start);

	// 构建cr annotation
	void clientReceiveRecord(Span span, Endpoint endpoint, long end,
			long duration);

	// 构建sr annotation
	void serverReceiveRecord(Span span, Endpoint endpoint, long start);

	// 构建 ss annotation
	void serverSendRecord(Span span, Endpoint endpoint, long end, int duration);

	Span newSpan(String spanName);

	Endpoint newEndPoint();

	boolean isSample();

	String genTracerId();

	String genSpanId();

	String genLogId();

	void setResultCode(Throwable t, Span span);

	void asyCatchUnExceptionLogSpan(Throwable throwable, String detail);

	String getTraceIdThreadLocal();

	String getSpanIdThreadLocal();

	String getServiceIdThreadLocal();

	SpanType getServiceTypeThreadLocal();
}
