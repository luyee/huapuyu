package com.anders.ssh.ioc;

/**
 * Setter注入
 * 
 * @author Anders
 * 
 */
public class SetterInjection
{
	private Pojo pojo;

	public Pojo getPojo()
	{
		return pojo;
	}

	public void setPojo(Pojo pojo)
	{
		this.pojo = pojo;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + "[id : " + this.pojo.getId() + "; name : " + this.pojo.getName() + "; score : " + this.pojo.getScore() + "]";
	}
}
