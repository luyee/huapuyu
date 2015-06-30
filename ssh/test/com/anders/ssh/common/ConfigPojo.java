package com.anders.ssh.common;

import org.springframework.stereotype.Component;

@Component
public class ConfigPojo {
	@Config(name = "load.init.data.enable")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
