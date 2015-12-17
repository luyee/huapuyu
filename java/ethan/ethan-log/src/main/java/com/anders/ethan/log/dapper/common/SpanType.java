package com.anders.ethan.log.dapper.common;

public enum SpanType {
	/**
	 * SQL(1)
	 */
	SQL("sql", "sql", 1),
	/**
	 * CACHE(2)
	 */
	CACHE("cache", "cache", 2),
	/**
	 * HTTP(3)
	 */
	HTTP("http", "http", 3),
	/**
	 * LOG(4)
	 */
	LOG("log", "log", 4),
	/**
	 * METRIC(5)
	 */
	METRIC("metric", "metric", 5),
	/**
	 * DUBBO(6)
	 */
	DUBBO("dubbo", "dubbo", 6),
	/**
	 * HTTP SERVER(7)
	 */
	HTTP_SERVER("http_server", "http_server", 7),
	/**
	 * DUBBO SERVER(8)
	 */
	DUBBO_SERVER("dubbo_server", "dubbo_server", 8),
	/**
	 * CODIS(9)
	 */
	CODIS("codis", "codis", 9),
	/**
	 * ROCKETMQ(10)
	 */
	ROCKETMQ("rocketmq", "rocketmq", 10),
	/**
	 * METHOD(11)
	 */
	METHOD("method", "method", 11);

	private String type;
	private String desc;
	private Integer value;

	SpanType(String type, String desc, Integer value) {
		this.type = type;
		this.desc = desc;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public Integer getValue() {
		return value;
	}

	public static SpanType findByType(String type) {
		for (SpanType spanType : SpanType.values()) {
			if (spanType.getType().equalsIgnoreCase(type)) {
				return spanType;
			}
		}

		return null;
	}

	public static SpanType findByValue(int value) {
		switch (value) {
		case 1:
			return SQL;
		case 2:
			return CACHE;
		case 3:
			return HTTP;
		case 4:
			return LOG;
		case 5:
			return METRIC;
		case 6:
			return DUBBO;
		case 7:
			return HTTP_SERVER;
		case 8:
			return DUBBO_SERVER;
		case 9:
			return CODIS;
		case 10:
			return ROCKETMQ;
		case 11:
			return METHOD;
		default:
			return null;
		}
	}
}
