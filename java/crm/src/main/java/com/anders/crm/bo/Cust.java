package com.anders.crm.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.anders.crm.utils.CustType;

/**
 * 客户
 * 
 * @author Anders Zhu
 * 
 */
@Setter
@Getter
@Entity
@Table(name = "tb_cust")
public class Cust extends BaseBO {

	private static final long serialVersionUID = 6424414021596996848L;

	/**
	 * 类型
	 */
	@Column(nullable = false)
	private CustType type;
	/**
	 * 名称
	 */
	@Column(nullable = false, length = 50)
	private String name;
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
	/**
	 * 联系人
	 */
	@ManyToMany(targetEntity = Contact.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_cust_to_contact", joinColumns = @JoinColumn(name = "cust_id"), inverseJoinColumns = @JoinColumn(name = "contact_id"))
	private List<Contact> contacts = new ArrayList<Contact>(0);
}
