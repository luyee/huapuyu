package com.anders.ssh.annotation;

import org.springframework.stereotype.Component;

@Component
public class ZhuZhen implements IZhuZhen
{
	private String name;
	private String relation;

	/*
	 * (non-Javadoc)
	 * 
	 * @see annotation.IZhuZhen#getName()
	 */
	public String getName()
	{
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see annotation.IZhuZhen#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see annotation.IZhuZhen#getRelation()
	 */
	public String getRelation()
	{
		return relation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see annotation.IZhuZhen#setRelation(java.lang.String)
	 */
	public void setRelation(String relation)
	{
		this.relation = relation;
	}

	public ZhuZhen()
	{
		this.name = "朱振";
		this.relation = "儿子";
	}

	@Override
	public String toString()
	{
		return "姓名：" + this.name + "；关系:" + this.relation;
	}
}
