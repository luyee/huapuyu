package com.anders.experiment.加密解密;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptTester {
	private static final Logger logger = Logger.getLogger(JasyptTester.class);

	public static void main(String[] args) throws UnsupportedEncodingException {
		BasicTextEncryptor textEncryptor1 = new BasicTextEncryptor();
		// 类似于salt
		textEncryptor1.setPassword("pass");
		String code1 = textEncryptor1.encrypt("zhuzhen");
		logger.debug("加密为：" + code1);
		logger.debug("解密为：" + textEncryptor1.decrypt(code1));

		BasicTextEncryptor textEncryptor2 = new BasicTextEncryptor();
		textEncryptor2.setPassword("fail");
		String code2 = textEncryptor2.encrypt("zhuzhen");
		logger.debug("加密为：" + code2);
		logger.debug("解密为：" + textEncryptor2.decrypt(code2));

		// String s1URLEncoder.encode("zhuzhen", "UTF-8")
		// logger.debug(URLEncoder.encode("zhuzhen", "UTF-8"));
		// logger.debug(URLDecoder.decode("zhuzhen", "UTF-8"));
	}
}
