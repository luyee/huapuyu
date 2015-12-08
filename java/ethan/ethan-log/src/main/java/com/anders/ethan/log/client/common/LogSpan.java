package com.anders.ethan.log.client.common;


import java.io.Serializable;
import java.util.UUID;

import com.anders.ethan.log.common.SpanType;


public class LogSpan implements Serializable{

	private static final long serialVersionUID = -3853336658209523388L;
	
	private String app;
	private String hostName;
	private String ip;
	private String instance;
	private String level;
	private String processId;
	private String threadId;
	private String logIdentity;
	private String method;
	private String logDatail;
	private String eventType;

    private String serviceId;
    private SpanType serviceType;
	private String traceId;
	private String spanId;
	private Long timestamp;
	private boolean isSample;
	private String logId;
	private String file;

    public SpanType getServiceType() {
        return serviceType;
    }

    public void setServiceType(SpanType serviceType) {
        this.serviceType = serviceType;
    }

    public LogSpan() {
		this.logId = String.valueOf(UUID.randomUUID().getLeastSignificantBits());
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public String getLogIdentity() {
		return logIdentity;
	}
	public void setLogIdentity(String logIdentity) {
		this.logIdentity = logIdentity;
	}
	public String getLogDatail() {
		return logDatail;
	}
	public void setLogDatail(String logDatail) {
		this.logDatail = logDatail;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}

	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public String getSpanId() {
		return spanId;
	}
	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public boolean isSample() {
		return isSample;
	}
	public void setSample(boolean isSample) {
		this.isSample = isSample;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    

    public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
    public String toString() {
        return "LogSpan{" +
                "app='" + app + '\'' +
                "ip='" + ip + '\'' +
                ", hostName='" + hostName + '\'' +
                ", instance='" + instance + '\'' +
                ", level='" + level + '\'' +
                ", processId='" + processId + '\'' +
                ", threadId='" + threadId + '\'' +
                ", logIdentity='" + logIdentity + '\'' +
                ", method='" + method + '\'' +
                ", logDatail='" + logDatail + '\'' +
                ", eventType='" + eventType + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", traceId='" + traceId + '\'' +
                ", spanId='" + spanId + '\'' +
                ", timestamp=" + timestamp +
                ", isSample=" + isSample +
                ", logId='" + logId + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
