package com.anders.ssh.bo.xml;

import java.io.Serializable;

public class House implements Serializable
{
	private static final long serialVersionUID = -6059443771590411309L;

	private Long id;
	private String name;
	private Area province;
	private Area city;
	private Area district;
	private String address;
	private Byte bedroomCount;
	private Byte livingRoomCount;
	private Byte kitchenCount;
	private Byte washroomCount;
	private Byte balconyCount;
	private Data propMgtType;
	private Data direction;
	private Data decoration;
	private Byte totalFloor;
	private Byte floor;
	private Data constructYear;
	private String transport;
	private String environment;
	private String remark;

	private RentHouse rentHouse;
	private SecondHandHouse secondHandHouse;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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

	public Area getProvince()
	{
		return province;
	}

	public void setProvince(Area province)
	{
		this.province = province;
	}

	public Area getCity()
	{
		return city;
	}

	public void setCity(Area city)
	{
		this.city = city;
	}

	public Area getDistrict()
	{
		return district;
	}

	public void setDistrict(Area district)
	{
		this.district = district;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Byte getBedroomCount()
	{
		return bedroomCount;
	}

	public void setBedroomCount(Byte bedroomCount)
	{
		this.bedroomCount = bedroomCount;
	}

	public Byte getLivingRoomCount()
	{
		return livingRoomCount;
	}

	public void setLivingRoomCount(Byte livingRoomCount)
	{
		this.livingRoomCount = livingRoomCount;
	}

	public Byte getKitchenCount()
	{
		return kitchenCount;
	}

	public void setKitchenCount(Byte kitchenCount)
	{
		this.kitchenCount = kitchenCount;
	}

	public Byte getWashroomCount()
	{
		return washroomCount;
	}

	public void setWashroomCount(Byte washroomCount)
	{
		this.washroomCount = washroomCount;
	}

	public Byte getBalconyCount()
	{
		return balconyCount;
	}

	public void setBalconyCount(Byte balconyCount)
	{
		this.balconyCount = balconyCount;
	}

	public Data getPropMgtType()
	{
		return propMgtType;
	}

	public void setPropMgtType(Data propMgtType)
	{
		this.propMgtType = propMgtType;
	}

	public Data getDirection()
	{
		return direction;
	}

	public void setDirection(Data direction)
	{
		this.direction = direction;
	}

	public Data getDecoration()
	{
		return decoration;
	}

	public void setDecoration(Data decoration)
	{
		this.decoration = decoration;
	}

	public Byte getTotalFloor()
	{
		return totalFloor;
	}

	public void setTotalFloor(Byte totalFloor)
	{
		this.totalFloor = totalFloor;
	}

	public Byte getFloor()
	{
		return floor;
	}

	public void setFloor(Byte floor)
	{
		this.floor = floor;
	}

	public Data getConstructYear()
	{
		return constructYear;
	}

	public void setConstructYear(Data constructYear)
	{
		this.constructYear = constructYear;
	}

	public String getTransport()
	{
		return transport;
	}

	public void setTransport(String transport)
	{
		this.transport = transport;
	}

	public String getEnvironment()
	{
		return environment;
	}

	public void setEnvironment(String environment)
	{
		this.environment = environment;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public RentHouse getRentHouse()
	{
		return rentHouse;
	}

	public void setRentHouse(RentHouse rentHouse)
	{
		this.rentHouse = rentHouse;
	}

	public SecondHandHouse getSecondHandHouse()
	{
		return secondHandHouse;
	}

	public void setSecondHandHouse(SecondHandHouse secondHandHouse)
	{
		this.secondHandHouse = secondHandHouse;
	}
}
