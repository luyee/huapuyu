package com.anders.ssh.log;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CallPK {
	private final static Log log = LogFactory.getLog(CallPK.class);

	private static final ThreadLocal<String> callPK = new ThreadLocal<String>();

	public String getCallPK() {
		return callPK.get();
	}

	public void setCallPK() {
		String uuid = UUID.randomUUID().toString();
		log.error("自动生成的UUID为：" + uuid);
		callPK.set(uuid);
	}

	public void setCallPK(String uuid) {
		log.error("手工指定的UUID为：" + uuid);
		callPK.set(uuid);
	}
}
