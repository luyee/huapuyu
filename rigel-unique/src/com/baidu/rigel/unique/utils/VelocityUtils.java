package com.baidu.rigel.unique.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class VelocityUtils {

	public static String escapeHTML(Number html) {
		if (Utils.isNotNull(html))
			return html.toString();
		return null;
	}

	public static String escapeHTML(Object value) {
		if (Utils.isNull(value) || Constant.NULL_STR.equals(value.toString()))
			return StringUtils.EMPTY;
		return StringEscapeUtils.escapeHtml(value.toString());
	}

	public static String escapeHTML(String html) {
		if (Utils.isNull(html))
			return StringUtils.EMPTY;
		return StringEscapeUtils.escapeHtml(html);
	}

	public static String escapeJS(Object value) {
		if (Utils.isNull(value) || Constant.NULL_STR.equals(value.toString()))
			return StringUtils.EMPTY;
		return StringEscapeUtils.escapeJavaScript(value.toString());
	}

	// TODO Anders Zhu : 重构
	public static String escapeAttr(Object value) {
		if (Utils.isNull(value) || Constant.NULL_STR.equals(value.toString()))
			return StringUtils.EMPTY;
		// return StringEscapeUtils.escapeXml(data.toString());
		return value.toString().replace("&", "&#38;").replace("\"", "&#34;").replace("'", "&#39;");
	}

	public static String replace(String str) {
		if (str == null || str.trim().equals("")) {
			return str;
		} else {
			if (str.startsWith("[")) {
				str = str.substring(1, str.length());
			}
			if (str.endsWith("]")) {
				str = str.substring(0, str.length() - 1);
			}
			if (str.startsWith(",")) {
				str = str.substring(1, str.length());
			}
			if (str.endsWith(",")) {
				str = str.substring(0, str.length() - 1);
			}
			return str;
		}
	}
}
