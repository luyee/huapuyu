package com.anders.vote.utils;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 安全工具类
 * 
 * @author Anders Zhu
 * 
 */
public class SecurityUtil {

	/**
	 * 根据账户名和明文密码生成加密密码
	 * 
	 * @param password
	 *            明文密码
	 * @param username
	 *            账户名
	 * @return 加密密码
	 */
	public static String getSha256Password(String password, String username) {
		// ByteSource byteSource = new SimpleByteSource(username);
		Hash hash = new SimpleHash(Constant.DEFAULT_ALGORITHM_NAME, password, ByteSource.Util.bytes(username), Constant.DEFAULT_HASH_ITERATIONS);
		return hash.toString();
	}

	/**
	 * 用于生成加密密码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// ByteSource byteSource = new SimpleByteSource("zhuzhen".toCharArray());
		ByteSource byteSource = ByteSource.Util.bytes("zhuzhen");
		System.out.println(byteSource);
		Hash hash = new SimpleHash(Constant.DEFAULT_ALGORITHM_NAME, "123456", byteSource, Constant.DEFAULT_HASH_ITERATIONS);
		System.out.println(hash);
		System.out.println(hash.toBase64());
		System.out.println(hash.toString());

		System.out.println("********************");

		// byteSource = new SimpleByteSource("admini".toCharArray());
		byteSource = ByteSource.Util.bytes("admini");
		System.out.println(byteSource);
		hash = new SimpleHash(Constant.DEFAULT_ALGORITHM_NAME, "123456", byteSource, Constant.DEFAULT_HASH_ITERATIONS);
		System.out.println(hash);
		System.out.println(hash.toBase64());
		System.out.println(hash.toString());

		System.out.println("********************");

		System.out.println(getSha256Password("123456", "admini"));
	}
}