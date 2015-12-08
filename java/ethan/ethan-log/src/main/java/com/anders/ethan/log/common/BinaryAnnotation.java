package com.anders.ethan.log.common;

import java.io.Serializable;

public class BinaryAnnotation implements Serializable {

	private static final long serialVersionUID = -8596016885469361056L;

	private String key;
	private String value;
	private String type;
	private Endpoint host;

	public Endpoint getHost() {
		return host;
	}

	public void setHost(Endpoint endpoint) {
		this.host = endpoint;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BinaryAnnotation{key=" + key + ", value=" + value + ", type="
				+ type + ", endpoint=" + host + "}";
	}
}
