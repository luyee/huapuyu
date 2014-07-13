package com.anders.crm.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.anders.crm.utils.ContactInfoType;

/**
 * 联系方式
 * 
 * @author Anders Zhu
 * 
 */
@Setter
@Getter
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
	/**
	 * 备注
	 */
	@Column(length = 500)
	private String remark;

}
