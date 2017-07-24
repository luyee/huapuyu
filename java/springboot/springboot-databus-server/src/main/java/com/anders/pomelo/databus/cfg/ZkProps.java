package com.anders.pomelo.databus.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zk")
public class ZkProps {

	private String address;
	private String node = "/databus/server/leader";

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}
}
