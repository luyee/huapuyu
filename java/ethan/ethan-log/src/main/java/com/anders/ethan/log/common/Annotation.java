package com.anders.ethan.log.common;

import java.io.Serializable;

public class Annotation implements Serializable {

	private static final long serialVersionUID = 2158804694101984573L;

	public static final String CLIENT_SEND = "cs";
	public static final String CLIENT_RECEIVE = "cr";
	public static final String SERVER_SEND = "ss";
	public static final String SERVER_RECEIVE = "sr";

	private Long timestamp;
	private String value;
	private Endpoint host;
	private Long duration;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Endpoint getHost() {
		return host;
	}

	public void setHost(Endpoint host) {
		this.host = host;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Annotation{timestamp=" + timestamp + ", value=" + value
				+ ", host=" + host + ", duration=" + duration + "}";
	}
}
