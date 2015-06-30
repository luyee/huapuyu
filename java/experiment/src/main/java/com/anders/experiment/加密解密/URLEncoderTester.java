package com.anders.experiment.加密解密;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLEncoderTester {
	private static final Logger LOG = LoggerFactory.getLogger(URLEncoderTester.class);

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = URLEncoder.encode("http://www.anders.com?name=朱振", "UTF-8");
		LOG.debug(url);
		LOG.debug(URLDecoder.decode(url, "UTF-8"));
	}
}
