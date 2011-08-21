package com.anders.ssh.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserGroup implements Serializable
{
	private static final long serialVersionUID = -7550715239549912247L;

	private Integer id;
	private String name;
	private Boolean enable = true;
	private Set<User> userSet = new HashSet<User>(0);
	private Set<Role> roleSet = new HashSet<Role>(0);

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}

	public Set<User> getUserSet()
	{
		return userSet;
	}

	public void setUserSet(Set<User> userSet)
	{
		this.userSet = userSet;
	}

	public Set<Role> getRoleSet()
	{
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet)
	{
		this.roleSet = roleSet;
	}

}
