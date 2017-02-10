package com.anders.vote.vo;


/**
 * 用户VO
 * 
 * @author Anders Zhu
 * 
 */
public class UserVO extends BaseVO<Long> {

	private static final long serialVersionUID = 6424414021596996848L;

	/**
	 * 账号
	 */
	private String userName;
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

	// getter and setter

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
