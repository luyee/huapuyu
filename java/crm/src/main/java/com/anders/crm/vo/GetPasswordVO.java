package com.anders.crm.vo;

import java.io.Serializable;

/**
 * 找回密码VO
 * 
 * @author Anders Zhu
 * 
 */
public class GetPasswordVO implements Serializable {

	private static final long serialVersionUID = 8688097078942710951L;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 验证码
	 */
	private String securityCode;

	// getter and setter

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
}
