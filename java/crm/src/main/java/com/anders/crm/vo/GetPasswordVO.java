package com.anders.crm.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 找回密码VO
 * 
 * @author Anders Zhu
 * 
 */
@Setter
@Getter
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

}
