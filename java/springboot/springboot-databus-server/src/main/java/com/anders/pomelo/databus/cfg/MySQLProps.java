package com.anders.pomelo.databus.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mysql")
public class MySQLProps {

	private String host;
	private int port;
	private String username;
	private String password;
	private String binlogPosition;
	private String includeDatabases;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBinlogPosition() {
		return binlogPosition;
	}

	public void setBinlogPosition(String binlogPosition) {
		this.binlogPosition = binlogPosition;
	}

	public String getIncludeDatabases() {
		return includeDatabases;
	}

	public void setIncludeDatabases(String includeDatabases) {
		this.includeDatabases = includeDatabases;
	}

}
