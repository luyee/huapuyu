package com.anders.pomelo.databus.cfg;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "binlog")
public class BinlogProps {

	private String host;
	private int port;
	private String username;
	private String password;
	private String filename;
	private int position;
	private String includedDatabases;
	private String ignoredTables;

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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Set<String> getIncludedDatabases() {
		if (StringUtils.isBlank(includedDatabases)) {
			return new HashSet<String>();
		}

		return new HashSet<String>(Arrays.asList(includedDatabases.split(",")));
	}

	public void setIncludedDatabases(String includedDatabases) {
		this.includedDatabases = includedDatabases;
	}

	public Set<String> getIgnoredTables() {
		if (StringUtils.isBlank(ignoredTables)) {
			return new HashSet<String>();
		}

		return new HashSet<String>(Arrays.asList(ignoredTables.split(",")));
	}

	public void setIgnoredTables(String ignoredTables) {
		this.ignoredTables = ignoredTables;
	}
}
