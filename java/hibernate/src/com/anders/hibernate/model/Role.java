package com.anders.hibernate.model;

import java.util.Set;

public class Role
{
	private Integer id;
	private String name;
	private Boolean enable = true;
	private Set<User> users;
	private Set<Privilege> privileges;

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

	public Set<User> getUsers()
	{
		return users;
	}

	public void setUsers(Set<User> users)
	{
		this.users = users;
	}

	public Set<Privilege> getPrivileges()
	{
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges)
	{
		this.privileges = privileges;
	}
}
