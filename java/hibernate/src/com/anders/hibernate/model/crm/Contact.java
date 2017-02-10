package com.anders.hibernate.model.crm;

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

/**
 * 联系人
 * 
 * @author Anders Zhu
 * 
 */
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public List<ContactInfo> getPhones() {
		return phones;
	}

	public void setPhones(List<ContactInfo> phones) {
		this.phones = phones;
	}

	public List<ContactInfo> getMobiles() {
		return mobiles;
	}

	public void setMobiles(List<ContactInfo> mobiles) {
		this.mobiles = mobiles;
	}

	public List<ContactInfo> getFaxes() {
		return faxes;
	}

	public void setFaxes(List<ContactInfo> faxes) {
		this.faxes = faxes;
	}

	public List<ContactInfo> getEmails() {
		return emails;
	}

	public void setEmails(List<ContactInfo> emails) {
		this.emails = emails;
	}

	public List<ContactInfo> getQqs() {
		return qqs;
	}

	public void setQqs(List<ContactInfo> qqs) {
		this.qqs = qqs;
	}

	public List<ContactInfo> getWechats() {
		return wechats;
	}

	public void setWechats(List<ContactInfo> wechats) {
		this.wechats = wechats;
	}

	public List<ContactInfo> getWeibos() {
		return weibos;
	}

	public void setWeibos(List<ContactInfo> weibos) {
		this.weibos = weibos;
	}

	public List<ContactInfo> getLinkedins() {
		return linkedins;
	}

	public void setLinkedins(List<ContactInfo> linkedins) {
		this.linkedins = linkedins;
	}

	public List<ContactInfo> getSkypes() {
		return skypes;
	}

	public void setSkypes(List<ContactInfo> skypes) {
		this.skypes = skypes;
	}

	public List<ContactInfo> getMsns() {
		return msns;
	}

	public void setMsns(List<ContactInfo> msns) {
		this.msns = msns;
	}

}
