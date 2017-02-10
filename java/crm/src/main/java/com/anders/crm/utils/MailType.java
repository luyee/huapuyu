package com.anders.crm.utils;

/**
 * 邮件类型
 * 
 * @author Anders Zhu
 * 
 */
public enum MailType {
	/**
	 * 找回密码（get_password）
	 */
	GET_PASSWORD("get_password"),
	/**
	 * 注册个人用户（register_individual）
	 */
	REGISTER_INDIVIDUAL("register_individual");

	private String name;

	private MailType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
