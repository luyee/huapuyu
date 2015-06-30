package com.anders.ssh.bo.xml;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class UserGroup implements Serializable {
	private static final long serialVersionUID = -7550715239549912247L;

	private Integer id;
	private String name;
	private Boolean enable = true;
	private Set<User> users = new HashSet<User>(0);
	private Set<Role> roles = new HashSet<Role>(0);

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
