package com.anders.hibernate.model;

import java.io.Serializable;

public class Data implements Serializable
{
	private static final long serialVersionUID = -3076049534246559365L;

	private Integer id;
	private String name;
	private Byte type;
	private Boolean enable = true;

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

	public Byte getType()
	{
		return type;
	}

	public void setType(Byte type)
	{
		this.type = type;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}
}
