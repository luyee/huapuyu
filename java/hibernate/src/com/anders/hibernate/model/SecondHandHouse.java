package com.anders.hibernate.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SecondHandHouse implements Serializable
{
	private static final long serialVersionUID = 4779933743319140372L;

	private Long id;
	private House house;
	private BigDecimal price;
	private BigDecimal buildingArea;
	private BigDecimal usableArea;
	private Data propety;
	private Data propType;
	private Data propStruct;
	private Data constructType;
	private Data visitTime;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public House getHouse()
	{
		return house;
	}

	public void setHouse(House house)
	{
		this.house = house;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	public BigDecimal getBuildingArea()
	{
		return buildingArea;
	}

	public void setBuildingArea(BigDecimal buildingArea)
	{
		this.buildingArea = buildingArea;
	}

	public BigDecimal getUsableArea()
	{
		return usableArea;
	}

	public void setUsableArea(BigDecimal usableArea)
	{
		this.usableArea = usableArea;
	}

	public Data getPropety()
	{
		return propety;
	}

	public void setPropety(Data propety)
	{
		this.propety = propety;
	}

	public Data getPropType()
	{
		return propType;
	}

	public void setPropType(Data propType)
	{
		this.propType = propType;
	}

	public Data getPropStruct()
	{
		return propStruct;
	}

	public void setPropStruct(Data propStruct)
	{
		this.propStruct = propStruct;
	}

	public Data getConstructType()
	{
		return constructType;
	}

	public void setConstructType(Data constructType)
	{
		this.constructType = constructType;
	}

	public Data getVisitTime()
	{
		return visitTime;
	}

	public void setVisitTime(Data visitTime)
	{
		this.visitTime = visitTime;
	}
}
