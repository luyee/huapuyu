package com.anders.vote.bo;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户组BO
 * 
 * @author Anders Zhu
 * 
 */
public class UserGroup extends BaseBO<Long> {

	private static final long serialVersionUID = 2963700485353847916L;

	/**
	 * 主键
	 */
	public Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 角色
	 */
	private Set<Role> roles = new HashSet<Role>();
	/**
	 * 用户
	 */
	private Set<User> users = new HashSet<User>();

	// getter and setter

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
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
