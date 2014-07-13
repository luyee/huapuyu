package com.anders.crm.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 联系人
 * 
 * @author Anders Zhu
 * 
 */
@Setter
@Getter
@Entity
@Table(name = "tb_contact")
public class Contact extends BaseBO {

	private static final long serialVersionUID = 6424414021596996848L;

	/**
	 * 姓名
	 */
	@Column(nullable = false, length = 50)
	private String name;
	/**
	 * 称谓
	 */
	@Column(length = 50)
	private String title;
	/**
	 * 生日
	 */
	@Column
	private Date birthday;
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
	 * 客户
	 */
	@ManyToMany(mappedBy = "contacts", targetEntity = Cust.class, fetch = FetchType.LAZY)
	private List<Cust> custs = new ArrayList<Cust>();
	/**
	 * 座机
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> phones = new ArrayList<ContactInfo>(0);
	/**
	 * 手机
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> mobiles = new ArrayList<ContactInfo>(0);
	/**
	 * 传真
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> faxes = new ArrayList<ContactInfo>(0);
	/**
	 * 邮箱
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> emails = new ArrayList<ContactInfo>(0);
	/**
	 * QQ
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> qqs = new ArrayList<ContactInfo>(0);
	/**
	 * 微信
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> wechats = new ArrayList<ContactInfo>(0);
	/**
	 * 微博
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> weibos = new ArrayList<ContactInfo>(0);
	/**
	 * LinkedIn
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> linkedins = new ArrayList<ContactInfo>(0);
	/**
	 * Skype
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> skypes = new ArrayList<ContactInfo>(0);
	/**
	 * MSN
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "contact_id")
	private List<ContactInfo> msns = new ArrayList<ContactInfo>(0);
}
