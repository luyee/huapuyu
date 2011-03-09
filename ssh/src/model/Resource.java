package model;

import java.util.Set;

public class Resource
{
	private Integer id;
	private String type;
	private String value;
	private Boolean enable = true;
	private Set<Authority> Authorities;

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

	public Set<Authority> getAuthorities()
	{
		return Authorities;
	}

	public void setAuthorities(Set<Authority> authorities)
	{
		Authorities = authorities;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
