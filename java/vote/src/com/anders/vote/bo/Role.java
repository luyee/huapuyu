package com.anders.vote.bo;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色BO
 * 
 * @author Anders Zhu
 * 
 */
public class Role extends BaseBO<Long> {

	private static final long serialVersionUID = 3690197650654049848L;

	/**
	 * 主键
	 */
	public Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 标签
	 */
	private String role;
	/**
	 * 资源集合
	 */
	private Set<Url> urls = new HashSet<Url>();
	/**
	 * 用户集合
	 */
	private Set<User> users = new HashSet<User>();
	/**
	 * 用户组集合
	 */
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();

	// getter and setter

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Url> getUrls() {
		return urls;
	}

	public void setUrls(Set<Url> urls) {
		this.urls = urls;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
