/**
 * 
 */
package com.baidu.rigel.unique.facade;

import java.io.Serializable;

/**
 * @author YanBing
 * 
 */
@SuppressWarnings("serial")
public class AuditInfo implements Serializable {
	/**
	 * 数据ID
	 */
	private Long id;
	/**
	 * 客户名称
	 */
	private String name;
	/**
	 * 审核信息来源
	 */
	private Short autoAuditSource;
	/**
	 * 备注
	 */
	private String remark;

	public Short getAutoAuditSource() {
		return autoAuditSource;
	}

	public void setAutoAuditSource(Short autoAuditSource) {
		this.autoAuditSource = autoAuditSource;
	}

	public String getRemark() {
		if (remark == null) {
			remark = "";
		}
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
