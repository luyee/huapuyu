package com.anders.crm.utils;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 安全工具类
 * 
 * @author Anders Zhu
 * 
 */
public class SecurityUtil {

	/**
	 * 判断用户是否通过验证
	 * 
	 * @return 是否通过验证（true：通过；false：未通过）
	 */
	public static boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public static String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		else if (principal.equals(Constant.ANONYMOUS_USERNAME)) {
			return StringUtils.EMPTY;
		}
		else {
			return principal.toString();
		}
	}

	/**
	 * 获取随机明文密码
	 * 
	 * @return 随机明文密码
	 */
	public static String getRandomPassword() {
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= 5; i++) {
			sb.append(Constant.PASSWORD_CHARS[rand.nextInt(Constant.PASSWORD_CHARS.length)]);
		}
		return sb.toString();
	}

	/**
	 * 根据用户名和明文密码生成加密密码
	 * 
	 * @param password
	 *            明文密码
	 * @param username
	 *            用户名
	 * @return 加密密码
	 */
	public static String getSha256Password(String password, String username) {
		ShaPasswordEncoder sha = new ShaPasswordEncoder(256);
		sha.setEncodeHashAsBase64(true);
		return sha.encodePassword(password, username);
	}

	public static String getRandomUsername() {
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			sb.append(Constant.USERNAME_CHARS[rand.nextInt(Constant.USERNAME_CHARS.length)]);
		}
		return sb.toString();
	}

	/**
	 * 用于生成加密密码
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getSha256Password("123456", Constant.ADMINISTRATOR_USERNAME));
		System.out.println(getSha256Password("123456", "zhuzhen"));
		System.out.println(getRandomPassword());
	}
}