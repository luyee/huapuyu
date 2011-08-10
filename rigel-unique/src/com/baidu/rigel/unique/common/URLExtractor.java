package com.baidu.rigel.unique.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 判断URL合法性并抽取URL主域
 * 
 * @author YanBing
 * 
 */
public class URLExtractor {

	public static final String PROPERTIES_FILE = "domain.properties";

	public static final String CONFIG_COMDOMAIN = "comDomain";
	public static final String CONFIG_NATIONDOMAIN = "nationDomain";
	public static final String CONFIG_SECONDDOMAIN = "secondDomain";
	public static final String CONFIG_LOCALDOMAIN = "localDomain";
	public static final String CONFIG_COMSECONDDOMAIN = "comSecondDomain";

	public static final String CONFIG_SEPARATOR = " ";

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

	public static String getPreDomain(String url) {

		String domain = getDomain(url);

		if (domain == null)
			return null;

		int pos = domain.indexOf('.');

		if (pos <= 0) {
			return domain;
		}

		return domain.substring(0, pos + 1);
	}

	public static String getHost(String url) {

		if (url == null || url.length() == 0) {
			return null;
		}

		try {
			URL u = new URL(url);
			String host = u.getHost();

			if (host != null && host.length() > 0) {

				return host.toLowerCase();
			}

			return null;
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public static String getDomain(String url) {

		// 获取host
		String host = getHost(url);
		if (host == null || host.length() == 0) {
			return null;
		}

		// host校验
		if (!isDomain(host)) {
			return null;
		}

		String[] domains = host.split("\\.");
		int len = domains.length;

		// 只有一个域不合法
		if (len <= 1) {
			return null;
		}

		String lastDomain = "." + domains[len - 1];
		String secDomain = "." + domains[len - 2];
		String lastSecondDomain = secDomain + lastDomain;

		String thirdDomain = null;
		if (len >= 3) {
			thirdDomain = "." + domains[len - 3];
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
						if (thirdDomain != null) {
							mainDomain = thirdDomain + lastSecondDomain;
						}
					} else {
						mainDomain = lastSecondDomain;
					}

				} else {
					// 通用二级域名
					if (comSecondDomain.contains(secDomain)) {
						if (thirdDomain != null) {
							mainDomain = thirdDomain + lastSecondDomain;
						}
					} else {
						mainDomain = lastSecondDomain;
					}

				}

			}
		}

		if (mainDomain != null && mainDomain.length() > 1) {
			mainDomain = mainDomain.substring(1).toLowerCase();
		} else {
			mainDomain = host;
		}

		return mainDomain;
	}

	public static boolean isDomain(String domain) {

		if (domain == null || domain.length() == 0) {
			return false;
		}

		if (domain.startsWith(".") || domain.startsWith("-") || domain.endsWith(".") || domain.endsWith("-")) {
			return false;
		}

		String[] parts = domain.split("\\.");

		if (parts.length <= 1) {
			return false;
		} else {
			for (String part : parts) {
				if (!islegal(part)) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean islegal(String domain) {

		if (domain == null || domain.length() == 0) {
			return false;
		}

		for (int i = 0; i < domain.length(); i++) {
			char c = domain.charAt(i);

			if (ALLOWED_STR.indexOf(c) == -1) {
				if (!isChinese(c)) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

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
			String[] domain = value.split(CONFIG_SEPARATOR);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					comDomain.add(s.toLowerCase());
				}
			}
		}

		// nationDomain
		value = properties.getProperty(CONFIG_NATIONDOMAIN);
		if (value != null && value.length() > 0) {
			String[] domain = value.split(CONFIG_SEPARATOR);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					nationDomain.add(s.toLowerCase());
				}
			}
		}

		// secondDomain
		value = properties.getProperty(CONFIG_SECONDDOMAIN);
		if (value != null && value.length() > 0) {
			String[] domain = value.split(CONFIG_SEPARATOR);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					secondDomain.add(s.toLowerCase());
				}
			}
		}

		// localDomain
		value = properties.getProperty(CONFIG_LOCALDOMAIN);
		if (value != null && value.length() > 0) {
			String[] domain = value.split(CONFIG_SEPARATOR);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					localDomain.add(s.toLowerCase());
				}
			}
		}

		// comSecondDomain
		value = properties.getProperty(CONFIG_COMSECONDDOMAIN);
		if (value != null && value.length() > 0) {
			String[] domain = value.split(CONFIG_SEPARATOR);
			for (String s : domain) {
				if (s != null && s.length() > 0) {
					comSecondDomain.add(s.toLowerCase());
				}
			}
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String url = "http://andy_boxes.w203.bizcn.com/";
		System.out.println(getDomain(url));
	}

}
