package com.anders.hibernate.model;

import java.util.Set;

public class Privilege
{
	private Integer id;
	private String name;
	private Boolean enable = true;
	private Set<Role> roles;
	private Set<Resource> resources;

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

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public Set<Resource> getResources()
	{
		return resources;
	}

	public void setResources(Set<Resource> resources)
	{
		this.resources = resources;
	}
}
