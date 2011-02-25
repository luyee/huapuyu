package struts.action;

import model.House;

import com.opensymphony.xwork2.ActionSupport;

public class ShhAction extends ActionSupport
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
