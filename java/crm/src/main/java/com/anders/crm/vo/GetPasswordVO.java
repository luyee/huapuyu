package com.anders.crm.vo;

import java.io.Serializable;

public class GetPasswordVO implements Serializable {

	private static final long serialVersionUID = 8688097078942710951L;

	private String username;

	private String securityCode;

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
