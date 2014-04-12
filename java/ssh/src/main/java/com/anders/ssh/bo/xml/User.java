package com.anders.ssh.bo.xml;

import java.util.HashSet;
import java.util.Set;

@Deprecated
public class User {
	private static final long serialVersionUID = 5989698534331721397L;

	private Long id;
	/**
	 * login name, unique
	 */
	private String userName;
	/**
	 * user name, for example: Anders Zhu
	 */
	private String name;
	private String pwd;
	private Boolean enable = true;
	private Set<Role> roles = new HashSet<Role>(0);
	private Set<UserGroup> userGroups = new HashSet<UserGroup>(0);
	private Set<Resource> resources = new HashSet<Resource>(0);

	// *****************
	// getter and setter
	// *****************

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
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

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

}
