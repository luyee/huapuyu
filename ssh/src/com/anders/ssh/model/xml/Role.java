package com.anders.ssh.model.xml;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable
{
	private static final long serialVersionUID = -4225855582860642624L;

	private Integer id;
	private String name;
	private Boolean enable = true;
	private Set<Resource> resourceSet = new HashSet<Resource>(0);

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

	public Set<Resource> getResourceSet()
	{
		return resourceSet;
	}

	public void setResourceSet(Set<Resource> resourceSet)
	{
		this.resourceSet = resourceSet;
	}

}
