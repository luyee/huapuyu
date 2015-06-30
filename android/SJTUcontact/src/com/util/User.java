package com.util;

public class User {

	private Integer id;
	private String name;
	private String mobileNumber;
	private String postnum;
	private String module;
	private String job;
	private String jobnum;
	private String email;

	private Integer groupnum;
	private String homenum;
	private String address;

	private Boolean isCode;
	private Integer codeStyle;

	private String groupName;

	public User() {
		this.isCode = false;
	}

	public Integer getCodeStyle() {
		return codeStyle;
	}

	public void setCodeStyle(Integer codeStyle) {
		this.codeStyle = codeStyle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		if (!isCode)
			return name;
		return enCode(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		if (!isCode)
			return mobileNumber;
		return enCode(mobileNumber);
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Integer getGroupnum() {
		return groupnum;
	}

	public void setGroupnum(Integer groupnum) {
		this.groupnum = groupnum;
	}

	public String getGroupName() {
		if (!isCode)
			return groupName;
		return enCode(groupName);
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

	public String getEmail() {
		if (!isCode)
			return email;
		return enCode(email);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHomenum() {
		if (!isCode)
			return homenum;
		return enCode(homenum);
	}

	public void setHomenum(String homenum) {
		this.homenum = homenum;
	}

	public String getAddress() {
		if (!isCode)
			return address;
		return enCode(address);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPostnum(String postnum) {
		this.postnum = postnum;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public void setJobnum(String jobnum) {
		this.jobnum = jobnum;
	}

	public String getPostnum() {
		if (!isCode)
			return postnum;
		return enCode(postnum);
	}

	public String getModule() {
		if (!isCode)
			return module;
		return enCode(module);
	}

	public String getJob() {
		if (!isCode)
			return job;
		return enCode(job);
	}

	public String getJobnum() {
		if (!isCode)
			return jobnum;
		return enCode(jobnum);
	}

	private String enCode(String str) {
		return Tools.enCode(str, codeStyle);
	}
}
