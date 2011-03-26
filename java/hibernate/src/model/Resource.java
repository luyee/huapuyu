package model;

import java.util.Set;

public class Resource
{
	private Integer id;
	private String name;
	private Boolean enable = true;
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

	public Set<Privilege> getPrivileges()
	{
		return privileges;
	}

	public void setPrivileges(Set<Privilege> privileges)
	{
		this.privileges = privileges;
	}
}
