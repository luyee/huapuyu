package com.util;

public class User {
	
	private Integer id;
	private String name;
	private String mobileNumber;
	private String createdDate;
	private String modifiedDate;
	private Integer groupnum;
	private Boolean isCode ;
	
	private String groupName;

	public User() {
		this.isCode = false;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		if(!isCode) return name;
		return Tools.enCode(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		if(!isCode) return mobileNumber;
		return Tools.enCode(mobileNumber);
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCreatedDate() {
		if(!isCode) return createdDate;
		return Tools.enCode(createdDate);
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		if(!isCode) return modifiedDate;
		return Tools.enCode(modifiedDate);
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getGroupnum() {
		return groupnum;
	}

	public void setGroupnum(Integer groupnum) {
		this.groupnum = groupnum;
	}

	public String getGroupName() {
		if(!isCode) return groupName;
		return Tools.enCode(groupName);
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Boolean getIsCode() {
		return isCode;
	}

	public void setIsCode(Boolean isCode) {
		this.isCode = isCode;
	}
	
	
}
