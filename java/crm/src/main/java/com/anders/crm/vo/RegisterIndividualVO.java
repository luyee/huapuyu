package com.anders.crm.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 注册个人用户VO
 * 
 * @author Anders Zhu
 * 
 */
@Setter
@Getter
public class RegisterIndividualVO implements Serializable {

	private static final long serialVersionUID = 5958636033146655284L;

	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String EMAIL = "email";

	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 邮箱
	 */
	private String email;
}
