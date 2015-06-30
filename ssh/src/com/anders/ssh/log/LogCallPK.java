package com.anders.ssh.log;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogCallPK {
	private final static Log log = LogFactory.getLog(LogCallPK.class);

	private static final ThreadLocal<String> logCallPK = new ThreadLocal<String>();

	public String getLogCallPK() {
		return logCallPK.get();
	}

	public void setLogCallPK() {
		String uuid = UUID.randomUUID().toString();
		log.error("自动生成的UUID为：" + uuid);
		logCallPK.set(uuid);
	}

	public void setLogCallPK(String uuid) {
		log.error("手工指定的UUID为：" + uuid);
		logCallPK.set(uuid);
	}
}
