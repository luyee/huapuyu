package com.anders.vote.bo;

import java.util.HashSet;
import java.util.Set;

/**
 * URL BO
 * 
 * @author Anders Zhu
 * 
 */
public class Url extends BaseBO<Long> {

	private static final long serialVersionUID = 2414932009925142715L;

	/**
	 * 主键
	 */
	public Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * URL
	 */
	private String url;
	/**
	 * 角色集合
	 */
	private Set<Role> roles = new HashSet<Role>();

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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