package com.anders.vote.bo;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 * 
 * @author Anders Zhu
 * 
 */
public class User extends BaseBO {

	private static final long serialVersionUID = 6424414021596996848L;

	/**
	 * 账号
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
	/**
	 * 角色集合
	 */
	private Set<Role> roles = new HashSet<Role>();
	/**
	 * 用户组集合
	 */
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();

	// getter and setter

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

}
