package com.anders.crm.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import com.anders.crm.utils.ContactInfoType;

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
	 * 座机
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> phones = new ArrayList<ContactInfoType>(0);
	/**
	 * 手机
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> mobiles = new ArrayList<ContactInfoType>(0);
	/**
	 * 传真
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> faxes = new ArrayList<ContactInfoType>(0);
	/**
	 * 邮箱
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> emails = new ArrayList<ContactInfoType>(0);
	/**
	 * QQ
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> qqs = new ArrayList<ContactInfoType>(0);
	/**
	 * 微信
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> wechats = new ArrayList<ContactInfoType>(0);
	/**
	 * 微博
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> weibos = new ArrayList<ContactInfoType>(0);
	/**
	 * LinkedIn
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> linkedins = new ArrayList<ContactInfoType>(0);
	/**
	 * Skype
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> skypes = new ArrayList<ContactInfoType>(0);
	/**
	 * MSN
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "contact")
	private List<ContactInfoType> msns = new ArrayList<ContactInfoType>(0);
}
