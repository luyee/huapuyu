package com.anders.vote.utils;

import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

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
		ByteSource byteSource = new SimpleByteSource(username.toCharArray());
		Hash hash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, password, byteSource, Constant.DEFAULT_HASH_ITERATIONS);
		return hash.toString();
	}

	/**
	 * 用于生成加密密码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ByteSource byteSource = new SimpleByteSource("zhuzhen".toCharArray());
		System.out.println(byteSource);
		Hash hash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, "123456", byteSource, Constant.DEFAULT_HASH_ITERATIONS);
		System.out.println(hash);
		System.out.println(hash.toBase64());
		System.out.println(hash.toString());

		System.out.println("********************");

		byteSource = new SimpleByteSource("admini".toCharArray());
		System.out.println(byteSource);
		hash = new SimpleHash(Sha256Hash.ALGORITHM_NAME, "123456", byteSource, Constant.DEFAULT_HASH_ITERATIONS);
		System.out.println(hash);
		System.out.println(hash.toBase64());
		System.out.println(hash.toString());
	}
}