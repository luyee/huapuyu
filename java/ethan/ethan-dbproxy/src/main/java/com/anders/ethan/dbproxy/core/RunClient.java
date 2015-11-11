package com.anders.ethan.dbproxy.core;

import com.anders.ethan.dbproxy.mysql.SecurityUtil;

public class RunClient {
	public static void main(String[] args) throws Exception {
//		System.out.println(passwd());
//		
//		byte[] password = passwd();
//		for (byte b : password) {
//			System.out.println(b);
//		}

		 new ServerBackend().connect();
	}

	public static byte[] passwd() throws Exception {
		byte[] seed = { 0x3f, 0x36, 0x31, 0x31, 0x43, 0x3b, 0x41, 0x72, 0x3b,
				0x6c, 0x32, 0x52, 0x26, 0x64, 0x49, 0x21, 0x62, 0x50, 0x49,
				0x5b };
		System.out.println(seed.length);
		return SecurityUtil.scramble411("123".getBytes(), seed);
	}
}
