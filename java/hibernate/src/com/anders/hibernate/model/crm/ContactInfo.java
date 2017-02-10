package com.anders.hibernate.model.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 联系方式
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_contact_info")
public class ContactInfo extends BaseBO {

	private static final long serialVersionUID = 6424414021596996848L;

	/**
	 * 联系方式类型
	 */
	@Column(nullable = false)
	private ContactInfoType type;
	/**
	 * 联系方式内容
	 */
	@Column(nullable = false, length = 50)
	private String info;

	public ContactInfoType getType() {
		return type;
	}

	public void setType(ContactInfoType type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 备注
	 */
	@Column(length = 500)
	private String remark;

}
