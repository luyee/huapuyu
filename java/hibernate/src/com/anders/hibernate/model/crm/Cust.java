package com.anders.hibernate.model.crm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 客户
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_cust")
public class Cust extends BaseBO {

	private static final long serialVersionUID = 6424414021596996848L;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustType getType() {
		return type;
	}

	public void setType(CustType type) {
		this.type = type;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 名称
	 */
	@Column(nullable = false, length = 50)
	private String name;
	/**
	 * 客户类型
	 */
	@Column(nullable = false)
	private CustType type;
	/**
	 * 联系人
	 */
	@ManyToMany(targetEntity = Contact.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_cust_to_contact", joinColumns = @JoinColumn(name = "cust_id"), inverseJoinColumns = @JoinColumn(name = "contact_id"))
	private List<Contact> contacts = new ArrayList<Contact>(0);
	/**
	 * 网站
	 */
	@Column(length = 100)
	private String website;
	/**
	 * 地址
	 */
	@Column(length = 200)
	private String address;
	/**
	 * 备注
	 */
	@Column(length = 500)
	private String remark;
}
