package com.anders.vote.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Base Value Object
 * 
 * @author Anders Zhu
 * 
 */
@SuppressWarnings("serial")
public abstract class BaseVO<PK extends Serializable> implements Serializable {

	// @Override
	// public abstract String toString();

	// @Override
	// public abstract boolean equals(Object object);

	// @Override
	// public abstract int hashCode();

	/**
	 * 主键
	 */
	private PK id;
	/**
	 * 启用（true：启用；false：禁用）
	 */
	private Boolean enabled;
	/**
	 * 新增人
	 */
	private Long addUserId;
	/**
	 * 新增时间
	 */
	private Date addTime;
	/**
	 * 更新人
	 */
	private Long updateUserId;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	// getter and setter

	public Boolean isEnabled() {
		return enabled;
	}

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getAddUserId() {
		return addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
