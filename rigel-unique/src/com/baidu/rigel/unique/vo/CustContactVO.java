package com.baidu.rigel.unique.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户资料联系人
 * 
 * @author cm
 * @created 2010-9-21 下午01:39:50
 * @lastModified
 * @history
 */
public class CustContactVO implements java.io.Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>,｛该处说明该变量的含义及作用｝
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键,联系人id
	 */
	private Long contactId;
	/**
	 * 客户ID
	 */
	private Long custId;
	/**
	 * 姓名
	 */
	private String contactName;

	/**
	 * 办公邮箱
	 */
	private String custCompanyMail;

	/**
	 * 私人邮箱
	 */
	private String custPersonalMail;

	/**
	 * 职务
	 */
	private String contactDuty;

	/**
	 * 决策类型
	 */
	private Long policyType;

	/**
	 * 性别
	 */
	private Short gender;

	/**
	 * 备注
	 */
	private String contactRemark;

	private Long addUcid;
	private Date addTime;

	/**
	 * 失效标志
	 */
	private Short disabledFlag;

	/**
	 * 失效原因
	 */
	private String disabledReason;

	/**
	 * 失效时间
	 */
	private Date disabledTime;

	/**
	 * 联系电话
	 */
	private List<CustContactPhoneVO> contactPhones = new ArrayList<CustContactPhoneVO>();

	public List<CustContactPhoneVO> getContactPhones() {
		return contactPhones;
	}

	public void setContactPhones(List<CustContactPhoneVO> contactPhone) {
		this.contactPhones = contactPhone;
	}

	/** default constructor */
	public CustContactVO() {
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public Long getCustId() {
		return this.custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCustCompanyMail() {
		return custCompanyMail;
	}

	public void setCustCompanyMail(String custCompanyMail) {
		this.custCompanyMail = custCompanyMail;
	}

	public String getCustPersonalMail() {
		return custPersonalMail;
	}

	public void setCustPersonalMail(String custPersonalMail) {
		this.custPersonalMail = custPersonalMail;
	}

	public String getContactDuty() {
		return contactDuty;
	}

	public void setContactDuty(String contactDuty) {
		this.contactDuty = contactDuty;
	}

	public Long getPolicyType() {
		return this.policyType;
	}

	public void setPolicyType(Long policyType) {
		this.policyType = policyType;
	}

	public Short getGender() {
		return this.gender;
	}

	public void setGender(Short gender) {
		this.gender = gender;
	}

	public String getContactRemark() {
		return contactRemark;
	}

	public void setContactRemark(String contactRemark) {
		this.contactRemark = contactRemark;
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
		if (disabledFlag == null) {
			disabledFlag = (short) 0;
		}
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