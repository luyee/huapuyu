package com.anders.ethan.log.cat.client.log.logback;

import java.io.IOException;
import java.util.Map;

import ch.qos.logback.contrib.jackson.JacksonJsonFormatter;

import com.dianping.cat.Cat;
import com.dianping.cat.message.spi.MessageTree;

public class JacksonJsonFormatterEx extends JacksonJsonFormatter {

	@Override
	public String toJsonString(Map m) throws IOException {
		MessageTree messageTree = null;
		try {
			messageTree = Cat.getManager().getThreadLocalMessageTree();
		} catch (Throwable e) {
		}

		if (messageTree != null) {
			try {
				String traceId = messageTree.getRootMessageId();
				if (traceId == null) {
					traceId = Cat.getCurrentMessageId();
				}

				if (traceId != null && traceId.length() > 0) {
					m.put("traceId", traceId.replaceAll("-", ""));
				}
			} catch (Throwable e) {
			}
		}

		try {
			String exp = (String) m.get("exception");
			if (exp != null && exp.length() > 3072) {
				// TODO Anders 性能问题
				exp = exp.substring(0, 3072);
				m.put("exception", exp);
			}
		} catch (Throwable e) {
		}

		return super.toJsonString(m);
	}

}
