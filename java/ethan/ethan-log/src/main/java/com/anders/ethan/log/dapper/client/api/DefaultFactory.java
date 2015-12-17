package com.anders.ethan.log.dapper.client.api;

import com.anders.ethan.log.dapper.client.api.spi.ITraceFactory;
import com.anders.ethan.log.dapper.client.api.spi.TraceGenerator;
import com.anders.ethan.log.dapper.client.common.LogSpan;
import com.anders.ethan.log.dapper.common.BinaryAnnotation;
import com.anders.ethan.log.dapper.common.Endpoint;
import com.anders.ethan.log.dapper.common.Span;
import com.anders.ethan.log.dapper.common.SpanType;

public final class DefaultFactory implements ITraceFactory {
	@Override
	public TraceGenerator getTraceGen() {
		return DefaultTraceGenerator.INSTANCE;
	}

	/**
	 * do nothing impl
	 */
	private static class DefaultTraceGenerator implements TraceGenerator {

		public static final DefaultTraceGenerator INSTANCE = new DefaultTraceGenerator();

		private DefaultTraceGenerator() {
		}

		@Override
		public void removeParentSpan() {
			// do nothing
		}

		@Override
		public void sendLogSpan(LogSpan logSpan) {
			// do nothing
		}

		// @Override
		// public void sendMetricSpan(MetricSpan metricSpan) {
		// //do nothing
		// }

		@Override
		public Span getParentSpan() {
			return null;
		}

		@Override
		public void setParentSpan(Span span) {
			// do nothing
		}

		@Override
		public void addBinaryAnnotation(BinaryAnnotation b) {
			// do nothing
		}

		@Override
		public Span newSpan(String appName, String spanName, Endpoint endpoint,
				String serviceId) {
			return null;
		}

		@Override
		public Span genSpan(String appName, String traceId, String pid,
				String id, String spanName, boolean isSample, String serviceId,
				Endpoint endpoint) {
			return null;
		}

		@Override
		public void simpleRecord(Endpoint endpoint, long end, long duration,
				String value) {

		}

		@Override
		public void clientSendRecord(Span span, Endpoint endpoint, long start) {
			// do nothing
		}

		@Override
		public void clientReceiveRecord(Span span, Endpoint endpoint, long end,
				long duration) {
			// do nothing
		}

		@Override
		public void serverReceiveRecord(Span span, Endpoint endpoint, long start) {
			// do nothing
		}

		@Override
		public void serverSendRecord(Span span, Endpoint endpoint, long end,
				int duration) {
			// do nothing
		}

		@Override
		public Span newSpan(String spanName) {
			return null;
		}

		@Override
		public Endpoint newEndPoint() {
			return null;
		}

		@Override
		public boolean isSample() {
			return false;
		}

		@Override
		public String genTracerId() {
			return null;
		}

		@Override
		public String genSpanId() {
			return null;
		}

		@Override
		public String genLogId() {
			return null;
		}

		@Override
		public void setResultCode(Throwable t, Span span) {
			// do nothing
		}

		@Override
		public void asyCatchUnExceptionLogSpan(Throwable throwable,
				String detail) {
			// do nothing
		}

		@Override
		public String getTraceIdThreadLocal() {
			return null;
		}

		@Override
		public String getSpanIdThreadLocal() {
			return null;
		}

		@Override
		public String getServiceIdThreadLocal() {
			return null;
		}

		@Override
		public SpanType getServiceTypeThreadLocal() {
			return null;
		}
	}
}
