package com.vip.datasource.util;

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
}
