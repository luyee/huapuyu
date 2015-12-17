package com.anders.ethan.log.cat.client.common;

import java.io.Serializable;
import java.util.UUID;

public class Trace implements Serializable {

	private static final long serialVersionUID = 5226262219058570624L;

	private String id;
	private String name;
	private TraceType type;
	private long timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TraceType getType() {
		return type;
	}

	public void setType(TraceType type) {
		this.type = type;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void generateTraceId() {
		this.id = String.valueOf(UUID.randomUUID().getLeastSignificantBits())
				.replace("-", "");
	}

	@Override
	public String toString() {
		return null;
	}

}
