package com.baidu.rigel.unique.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 客户基本信息
 * 
 * @author cm
 * @created 2010-9-8 下午09:32:30
 * @lastModified
 * @history
 */
public class CustomerVO implements java.io.Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>,｛该处说明该变量的含义及作用｝
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 客户资料ID
	 */
	private Long custId;
	/**
	 * 客户类型 0:普通企业 1:特殊企业 2:个人客户 3:广告代理
	 */
	private Short custType;
	/**
	 * 客户名称
	 */
	private String custName;
	/**
	 * 分支名称
	 */
	private String branchName;
	/**
	 * 全名
	 */
	private String fullName;
	/**
	 * 部门ID
	 */
	private Long posid;
	/**
	 * 行业ID
	 */
	private Long trade;
	/**
	 * 个人客户备注
	 */
	private String perRemark;
	/**
	 * 客户备注
	 */
	private String remark;
	/**
	 * 客户建站情况 0:已有网站，1:没有网站，2:未知
	 */
	private Short siteType;
	/**
	 * 客户无网站说明类型
	 */
	private Short noSiteType;
	/**
	 * 网站名称
	 */
	private String siteName;
	/**
	 * 网址
	 */
	private String siteUrl;
	/**
	 * 网址主域
	 */
	private String siteDomain;

	/**
	 * 客户来源
	 */
	private Short sourceType;
	/**
	 * 入库方式
	 */
	private Short priType;
	/**
	 * 一级状态
	 */
	private String stat1;

	/**
	 * 当前in跟进人
	 */
	private Long inUcid;

	/**
	 * 当前out跟进人
	 */
	private Long outUcid;
	/**
	 * 添加人
	 */
	private Long addUcid;
	/**
	 * 添加时间
	 */
	private Date addTime;
	/**
	 * 修改人
	 */
	private Long updUcid;
	/**
	 * 修改时间
	 */
	private Date updTime;

	/**
	 * 仅仅用来传值
	 */
	private Object tempValue;

	/**
	 * @return 返回 tempValue.
	 */
	public Object getTempValue() {
		return tempValue;
	}

	/**
	 * @param tempValue
	 *            设置 tempValue
	 */
	public void setTempValue(Object tempValue) {
		this.tempValue = tempValue;
	}

	/**
	 * 联系人电话
	 */
	private List<String> phones = new ArrayList<String>();

	/** default constructor */
	public CustomerVO() {

	}

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custID) {
		this.custId = custID;
	}

	public Short getCustType() {
		return custType;
	}

	public void setCustType(Short custType) {
		this.custType = custType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getFullName() {
		if (fullName == null) {
			if (StringUtils.hasText(getBranchName())) {
				fullName = custName + getBranchName();
			} else {
				fullName = custName;
			}
		}
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getPosid() {
		return this.posid;
	}

	public void setPosid(Long posid) {
		this.posid = posid;
	}

	public Long getTrade() {
		return this.trade;
	}

	public void setTrade(Long trade) {
		this.trade = trade;
	}

	public String getPerRemark() {
		return perRemark;
	}

	public void setPerRemark(String perRemark) {
		this.perRemark = perRemark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getSiteType() {
		return this.siteType;
	}

	public void setSiteType(Short siteType) {
		this.siteType = siteType;
	}

	public Short getNoSiteType() {
		return this.noSiteType;
	}

	public void setNoSiteType(Short noSiteType) {
		this.noSiteType = noSiteType;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteUrl() {
		return this.siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}

	public String getSiteDomain() {
		return this.siteDomain;
	}

	public void setSiteDomain(String siteDomain) {
		this.siteDomain = siteDomain;
	}

	public Short getSourceType() {
		return sourceType;
	}

	public void setSourceType(Short sourceType) {
		this.sourceType = sourceType;
	}

	public Short getPriType() {
		return priType;
	}

	public void setPriType(Short priType) {
		this.priType = priType;
	}

	public String getStat1() {
		return stat1;
	}

	public void setStat1(String stat1) {
		this.stat1 = stat1;
	}

	public Long getInUcid() {
		return inUcid;
	}

	public void setInUcid(Long inUcid) {
		this.inUcid = inUcid;
	}

	public Long getOutUcid() {
		return outUcid;
	}

	public void setOutUcid(Long outUcid) {
		this.outUcid = outUcid;
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

	public Long getUpdUcid() {
		return this.updUcid;
	}

	public void setUpdUcid(Long updUcid) {
		this.updUcid = updUcid;
	}

	public Date getUpdTime() {
		return this.updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	/**
	 * @return 返回 phones.
	 */
	public List<String> getPhones() {
		return phones;
	}

	/**
	 * @param phones
	 *            设置 phones
	 */
	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	// TODO Anders Zhu : 以下为新添加
	/**
	 * 联系人
	 */
	private List<CustContactVO> custContacts = new ArrayList<CustContactVO>();

	public List<CustContactVO> getCustContacts() {
		return custContacts;
	}

	public void setCustContacts(List<CustContactVO> custContacts) {
		this.custContacts = custContacts;
	}
}