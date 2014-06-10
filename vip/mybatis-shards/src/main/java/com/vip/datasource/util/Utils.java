package com.vip.datasource.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.PatternMatchUtils;

public class Utils {

	public static boolean isMatch(String methodName, String mappedName) {
		return PatternMatchUtils.simpleMatch(mappedName, methodName);
	}

	public static boolean isRead(String key) {
		return Constants.READ_KEY.equalsIgnoreCase(key);
	}

	public static boolean isWrite(String key) {
		return Constants.WRITE_KEY.equalsIgnoreCase(key);
	}

	public static boolean isBasicType(Object o) {
		return o.getClass().isPrimitive() | o instanceof Integer | o instanceof Long | o instanceof Short | o instanceof Byte | o instanceof Boolean | o instanceof Double | o instanceof Float | o instanceof String;
	}

	public static boolean isMapType(Object o) {
		return o instanceof Map<?, ?>;
	}

	public static String getMatchTableName(String sql) {
		Pattern pattern = Pattern.compile("\\$\\[.*\\]\\$");
		Matcher matcher = pattern.matcher(sql);
		while (matcher.find()) {
			String name = matcher.group();
			return name.substring(2, name.length() - 2);
		}
		throw new RuntimeException("can not find match table name");
	}

	public static void main(String[] args) {
		System.out.println(isBasicType(1));

		System.out.println(getMatchTableName("agfasdfa$[user]$asdfasdfas"));
	}
}
