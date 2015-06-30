package com.anders.crm.utils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * 安全工具类
 * 
 * @author Anders Zhu
 * 
 */
public class CommonUtil {

	public static String getLocale(HttpServletRequest request) {
		Locale locale = request.getLocale();
		return String.format("%s_%s", locale.getLanguage(), locale.getCountry());
	}

}