package com.baidu.rigel.unique.bo;

import java.util.HashMap;
import java.util.Map;

public class UrlWhitelist extends UrlWhitelistBase {

	// TODO Anders Zhu : 重构
	public static Map<Short, String> typeMap = new HashMap<Short, String>();

	public static short shortPeriod = 0;

	public static short longPeriod = 1;

	static {
		typeMap.put(shortPeriod, "短期");
		typeMap.put(longPeriod, "长期");
	}

	public String getTypeLabel(Short type) {
		if (type == null)
			return "";
		return typeMap.get(type);
	}
}