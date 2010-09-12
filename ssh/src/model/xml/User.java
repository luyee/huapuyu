package model.xml;

import java.util.Set;

public class User
{
	private Integer id;
	private String name;
	private String pwd;
	private Byte status;
	private String describe;
	private Set<Role> roles;

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public User()
	{
	}

	public User(String name, String pwd)
	{
		this.name = name;
		this.pwd = pwd;
	}

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

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public Byte getStatus()
	{
		return status;
	}

	public void setStatus(Byte status)
	{
		this.status = status;
	}

	public String getDescribe()
	{
		return describe;
	}

	public void setDescribe(String describe)
	{
		this.describe = describe;
	}
}
