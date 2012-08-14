package com.anders.crm.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base Business Object
 * 
 * @author Anders Zhu
 * 
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class BaseBO implements Serializable {

	// @Override
	// public String toString() {
	// return getClass().getName() + "@" + Integer.toHexString(hashCode());
	// }

	// @Override
	// public abstract boolean equals(Object object);

	// @Override
	// public abstract int hashCode();

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 启用（true：启用；false：禁用）
	 */
	@Column(nullable = false)
	private boolean enabled = true;
	/**
	 * 新增人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "add_user_id", nullable = false)
	private User addUser;
	/**
	 * 新增时间
	 */
	@Column(name = "add_time", nullable = false)
	private Date addTime = new Date();
	/**
	 * 更新人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "update_user_id")
	private User updateUser;
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;
	/**
	 * 乐观锁
	 */
	@Version
	private Integer version;

	// getter and setter

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public User getAddUser() {
		return addUser;
	}

	public void setAddUser(User addUser) {
		this.addUser = addUser;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
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
