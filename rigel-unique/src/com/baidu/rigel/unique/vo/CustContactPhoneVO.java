package com.baidu.rigel.unique.vo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.baidu.rigel.unique.utils.PhoneType;

public class CustContactPhoneVO implements java.io.Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>,｛该处说明该变量的含义及作用｝
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Long phoneId;
	/**
	 * 客户ID
	 */
	private Long custId;
	/**
	 * 联系人ID
	 */
	private Long contactId;
	/**
	 * 电话类型,0座机,1手机
	 */
	private Short phoneType;
	/**
	 * 国家号码
	 */
	private String countryNum;
	/**
	 * 区号
	 */
	private String areaNum;

	/**
	 * 电话号码
	 */
	private String phoneNum;
	/**
	 * 分机号
	 */
	private String extNum;

	/**
	 * 包括区号
	 */
	private String fullPhoneNum;

	public String getFullPhoneNum() {
		fullPhoneNum = (StringUtils.hasText(countryNum) ? countryNum + "-" : "") + getFullPhone() + (!StringUtils.hasText(extNum) ? "" : "-" + extNum);
		return fullPhoneNum;
	}

	public void setFullPhoneNum(String fullPhoneNum) {
		this.fullPhoneNum = fullPhoneNum;
	}

	/**
	 * 电话号码,包括区号
	 */
	private String fullPhone;

	private Long addUcid;
	private Date addTime;
	private Short disabledFlag;
	private String disabledReason;
	private Date disabledTime;

	/** default constructor */
	public CustContactPhoneVO() {
	}

	public Long getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(Long phoneId) {
		this.phoneId = phoneId;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public Long getContactId() {
		return this.contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public Short getPhoneType() {
		return this.phoneType;
	}

	public void setPhoneType(Short phoneType) {
		this.phoneType = phoneType;
	}

	public String getCountryNum() {
		return this.countryNum;
	}

	public void setCountryNum(String countryNum) {
		this.countryNum = countryNum;
	}

	public String getAreaNum() {
		return this.areaNum;
	}

	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}

	public String getPhoneNum() {
		return this.phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getExtNum() {
		return this.extNum;
	}

	public void setExtNum(String extNum) {
		this.extNum = extNum;
	}

	public String getFullPhone() {
		if (fullPhone == null) {
			if (StringUtils.hasText(phoneNum)) {
				if (phoneType != null && phoneType.shortValue() == PhoneType.TELEPHONE.getValue() && StringUtils.hasText(areaNum)) {
					fullPhone = areaNum + "-" + phoneNum;
				} else {
					fullPhone = phoneNum;
				}
			}
		}
		return this.fullPhone;
	}

	public void setFullPhone(String fullPhone) {
		this.fullPhone = fullPhone;
	}

	public Long getAddUcid() {
		return this.addUcid;
	}

	public void setAddUcid(Long addUcid) {
		this.addUcid = addUcid;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Short getDisabledFlag() {
		if (disabledFlag == null)
			disabledFlag = (short) 0;
		return this.disabledFlag;
	}

	public void setDisabledFlag(Short disabledFlag) {
		this.disabledFlag = disabledFlag;
	}

	public String getDisabledReason() {
		return this.disabledReason;
	}

	public void setDisabledReason(String disabledReason) {
		this.disabledReason = disabledReason;
	}

	public Date getDisabledTime() {
		return this.disabledTime;
	}

	public void setDisabledTime(Date disabledTime) {
		this.disabledTime = disabledTime;
	}

}