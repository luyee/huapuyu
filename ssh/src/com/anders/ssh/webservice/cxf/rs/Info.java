package com.anders.ssh.webservice.cxf.rs;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@XmlRootElement(name = "INFO")
public class Info implements Serializable
{
	private static final long serialVersionUID = -6393308495112525734L;

	private String id;
	private String name;
	private String desc;

	public Info()
	{
	}

	public Info(String id, String name, String desc)
	{
		this.id = id;
		this.name = name;
		this.desc = desc;
	}

	@XmlElement(name = "ID")
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	@XmlElement(name = "NAME")
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@XmlElement(name = "DESC")
	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	@Override
	public String toString()
	{
		return ReflectionToStringBuilder.toString(this);
	}
}
