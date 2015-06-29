package com.anders.vote.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * Base Business Object
 * 
 * @author Anders Zhu
 * 
 */
@SuppressWarnings("serial")
public abstract class BaseBO<PK extends Serializable> implements Serializable {

	// @Override
	// public abstract String toString();

	// @Override
	// public abstract boolean equals(Object object);

	// @Override
	// public abstract int hashCode();

	/**
	 * 启用（true：启用；false：禁用）
	 */
	private Boolean enabled = true;
	/**
	 * 新增人
	 */
	private Long addUserId;
	/**
	 * 新增时间
	 */
	private Date addTime = new Date();
	/**
	 * 更新人
	 */
	private Long updateUserId;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 乐观锁
	 */
	private Integer version = 0;

	// getter and setter

	public Boolean isEnabled() {
		return enabled;
	}

	public abstract PK getId();

	public abstract void setId(PK id);

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
