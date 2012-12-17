package com.anders.ssh.bo.xml;

import java.io.Serializable;
import java.math.BigDecimal;

public class RentHouse implements Serializable {
	private static final long serialVersionUID = 3260501110004352964L;

	private Long id;
	private House house;
	private BigDecimal price;
	private BigDecimal area;
	private Byte type;
	private Data shareType;
	private Data roommateGender;
	private Data payment;
	private Data checkIn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Data getShareType() {
		return shareType;
	}

	public void setShareType(Data shareType) {
		this.shareType = shareType;
	}

	public Data getRoommateGender() {
		return roommateGender;
	}

	public void setRoommateGender(Data roommateGender) {
		this.roommateGender = roommateGender;
	}

	public Data getPayment() {
		return payment;
	}

	public void setPayment(Data payment) {
		this.payment = payment;
	}

	public Data getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Data checkIn) {
		this.checkIn = checkIn;
	}
}
