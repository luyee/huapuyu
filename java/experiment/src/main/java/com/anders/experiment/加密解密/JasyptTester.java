package com.anders.experiment.加密解密;

import java.io.UnsupportedEncodingException;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JasyptTester {
	private static final Logger LOG = LoggerFactory.getLogger(JasyptTester.class);

	public static void main(String[] args) throws UnsupportedEncodingException {
		BasicTextEncryptor textEncryptor1 = new BasicTextEncryptor();
		// 类似于salt
		textEncryptor1.setPassword("pass");
		String code1 = textEncryptor1.encrypt("zhuzhen");
		LOG.debug("加密为：" + code1);
		LOG.debug("解密为：" + textEncryptor1.decrypt(code1));

		BasicTextEncryptor textEncryptor2 = new BasicTextEncryptor();
		textEncryptor2.setPassword("fail");
		String code2 = textEncryptor2.encrypt("zhuzhen");
		LOG.debug("加密为：" + code2);
		LOG.debug("解密为：" + textEncryptor2.decrypt(code2));

		// String s1URLEncoder.encode("zhuzhen", "UTF-8")
		// logger.debug(URLEncoder.encode("zhuzhen", "UTF-8"));
		// logger.debug(URLDecoder.decode("zhuzhen", "UTF-8"));
	}
}
