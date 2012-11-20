package com.anders.ssh.struts.action;

import com.anders.ssh.model.xml.House;

public class ShhAction extends BaseAction
{
	private static final long serialVersionUID = -7548709763761263808L;

	private House house;

	public String add()
	{
		return SUCCESS;
	}

	public String addAction()
	{
		return SUCCESS;
	}

	public House getHouse()
	{
		return house;
	}

	public void setHouse(House house)
	{
		this.house = house;
	}

}
