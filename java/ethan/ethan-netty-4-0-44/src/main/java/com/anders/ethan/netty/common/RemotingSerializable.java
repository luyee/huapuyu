package com.anders.ethan.netty.common;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;

public class RemotingSerializable {

	public static String toJson(final Object obj, boolean prettyFormat) {
		return JSON.toJSONString(obj, prettyFormat);
	}

	public static <T> T fromJson(String json, Class<T> classOfT) {
		return JSON.parseObject(json, classOfT);
	}

	public static byte[] encode(final Object obj) {
		final String json = toJson(obj, false);
		if (json != null) {
			return json.getBytes(Charset.forName("UTF-8"));
		}
		return null;
	}

	public static <T> T decode(final byte[] data, Class<T> classOfT) {
		final String json = new String(data, Charset.forName("UTF-8"));
		return fromJson(json, classOfT);
	}
}
