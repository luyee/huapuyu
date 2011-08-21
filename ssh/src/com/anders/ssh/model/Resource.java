package com.anders.ssh.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Resource implements Serializable
{
	private static final long serialVersionUID = -8246262126931423368L;

	private Integer id;
	private String type;
	private String url;
	private String name;
	private Boolean enable = true;
	private Set<Role> roleSet = new HashSet<Role>(0);

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
