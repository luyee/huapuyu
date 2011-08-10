/**
 * 
 */
package com.baidu.rigel.unique.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 判断URL合法性并抽取URL主域
 * 
 * @author Anders Zhu
 * 
 */
public class URLUtils {

	private static Log log = LogFactory.getLog(URLUtils.class);

	public static final String PROPERTIES_FILE = "domain.properties";

	public static final String CONFIG_COMDOMAIN = "comDomain";
	public static final String CONFIG_NATIONDOMAIN = "nationDomain";
	public static final String CONFIG_SECONDDOMAIN = "secondDomain";
	public static final String CONFIG_LOCALDOMAIN = "localDomain";
	public static final String CONFIG_COMSECONDDOMAIN = "comSecondDomain";

	private static final String ALLOWED_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-1234567890_";

	// 通用域名
	private static final Set<String> comDomain = new HashSet<String>();
	// 国家域名
	private static final Set<String> nationDomain = new HashSet<String>();
	// 二级域名
	private static final Set<String> secondDomain = new HashSet<String>();
	// 中国地区域名
	private static final Set<String> localDomain = new HashSet<String>();
	// 通用二级域名
	private static final Set<String> comSecondDomain = new HashSet<String>();

	static {
		parseConfigFile();
	}

	// TODO Anders Zhu ： 什么意思
	public static String getPreDomain(String url) {

		String domain = getDomain(url);

		if (Utils.isNull(domain))
			return null;

		int pos = domain.indexOf(Constant.DOT);

		if (Utils.isLessEqualThanZero(pos))
			return domain;

		return domain.substring(0, pos + 1);
	}

	public static String getHost(String url) {
		if (StringUtils.isEmpty(url))
			return null;

		try {
			URL u = new URL(url);
			String host = u.getHost();

			if (StringUtils.isNotEmpty(host))
				return host.toLowerCase();

			return null;
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
			return null;
		}
	}

	// TODO Anders Zhu : 重构+理解
	public static String getDomain(String url) {

		// 获取host
		String host = getHost(url);
		// if (host == null || host.length() == 0) {
		if (StringUtils.isEmpty(host))
			return null;

		// host校验
		if (!isDomain(host)) {
			return null;
		}

		String[] domains = host.split("\\.");
		int len = domains.length;

		// 只有一个域不合法
		if (len <= Constant.ONE) {
			return null;
		}

		String lastDomain = Constant.DOT + domains[len - 1];
		String secDomain = Constant.DOT + domains[len - 2];
		String lastSecondDomain = secDomain + lastDomain;

		String thirdDomain = null;
		if (len >= 3) {
			thirdDomain = Constant.DOT + domains[len - 3];
		}

		String mainDomain = null;
		// 通用域名
		if (comDomain.contains(lastDomain)) {
			mainDomain = lastSecondDomain;

		} else {
			// 国家域名
			if (nationDomain.contains(lastDomain)) {
				// cn,hk,tw
				if (localDomain.contains(lastDomain)) {
					// 二级域名
					if (secondDomain.contains(lastSecondDomain)) {
						if (Utils.isNotNull(thirdDomain)) {
							mainDomain = thirdDomain + lastSecondDomain;
						}
					} else {
						mainDomain = lastSecondDomain;
					}

				} else {
					// 通用二级域名
					if (comSecondDomain.contains(secDomain)) {
						if (Utils.isNotNull(thirdDomain)) {
							mainDomain = thirdDomain + lastSecondDomain;
						}
					} else {
						mainDomain = lastSecondDomain;
					}

				}

			}
		}

		if (Utils.isNotNull(mainDomain) && mainDomain.length() > 1) {
			mainDomain = mainDomain.substring(1).toLowerCase();
		} else {
			mainDomain = host;
		}

		return mainDomain;
	}

	public static boolean isDomain(String domain) {
		if (StringUtils.isEmpty(domain))
			return Boolean.FALSE;

		if (domain.startsWith(Constant.DOT) || domain.startsWith(Constant.HYPHEN) || domain.endsWith(Constant.DOT) || domain.endsWith(Constant.HYPHEN))
			return Boolean.FALSE;

		// TODO Anders Zhu : 重构
		String[] parts = domain.split("\\.");

		if (parts.length <= Constant.ONE) {
			return Boolean.FALSE;
		} else {
			for (String part : parts) {
				if (!islegal(part)) {
					return Boolean.FALSE;
				}
			}
		}

		return Boolean.TRUE;
	}

	private static boolean islegal(String domain) {
		if (StringUtils.isEmpty(domain))
			return Boolean.FALSE;

		for (int i = 0; i < domain.length(); i++) {
			char c = domain.charAt(i);
			if (Constant.MINUS_ONE == ALLOWED_STR.indexOf(c)) {
				if (!isChinese(c)) {
					return Boolean.FALSE;
				}
			}
		}

		return Boolean.TRUE;
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	// TODO Anders Zhu ： 重构+理解
	private static void parseConfigFile() {

		Properties properties = new Properties();

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
			return;

		InputStream stream = classLoader.getResourceAsStream(PROPERTIES_FILE);
		if (stream == null) {
			return;
		}

		try {
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// comDomain
		String value = properties.getProperty(CONFIG_COMDOMAIN);
		if (value != null && value.length() > 0) {
			String[] domain = value.split(Constant.BLANK);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					comDomain.add(s.toLowerCase());
				}
			}
		}

		// nationDomain
		value = properties.getProperty(CONFIG_NATIONDOMAIN);
		if (value != null && value.length() > 0) {
			String[] domain = value.split(Constant.BLANK);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					nationDomain.add(s.toLowerCase());
				}
			}
		}

		// secondDomain
		value = properties.getProperty(CONFIG_SECONDDOMAIN);
		if (value != null && value.length() > 0) {
			String[] domain = value.split(Constant.BLANK);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					secondDomain.add(s.toLowerCase());
				}
			}
		}

		// localDomain
		value = properties.getProperty(CONFIG_LOCALDOMAIN);
		if (value != null && value.length() > 0) {
			String[] domain = value.split(Constant.BLANK);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					localDomain.add(s.toLowerCase());
				}
			}
		}

		// comSecondDomain
		value = properties.getProperty(CONFIG_COMSECONDDOMAIN);
		if (value != null && value.length() > 0) {
			String[] domain = value.split(Constant.BLANK);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					comSecondDomain.add(s.toLowerCase());
				}
			}
		}
	}

}
