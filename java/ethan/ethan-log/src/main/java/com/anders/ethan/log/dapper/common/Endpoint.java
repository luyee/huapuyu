package com.anders.ethan.log.dapper.common;

import java.io.Serializable;

public class Endpoint implements Serializable {

	private static final long serialVersionUID = 8904854367325313249L;

	// TODO Anders 手工提取host和port
	private String host;
	private String ip;
	private Integer port;

	public Endpoint() {
	}

	public Endpoint(String host, Integer port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "Endpoint{host=" + host + ", ip=" + ip + ", port=" + port + "}";
	}
}
