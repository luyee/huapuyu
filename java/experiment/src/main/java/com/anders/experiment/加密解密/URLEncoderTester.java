package com.anders.experiment.加密解密;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class URLEncoderTester {
	private static final Logger logger = Logger
			.getLogger(URLEncoderTester.class);

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = URLEncoder
				.encode("http://www.anders.com?name=朱振", "UTF-8");
		logger.debug(url);
		logger.debug(URLDecoder.decode(url, "UTF-8"));
	}
}
