package com.anders.crm.bo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 资源
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_resource")
public class Resource extends BaseBO {

	private static final long serialVersionUID = 2414932009925142715L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 资源名
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	// TODO Anders Zhu : 这个名字合适吗？
	/**
	 * 资源内容
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String content;
	/**
	 * 启用（0：启用；1：禁用）
	 */
	@Column(nullable = false)
	private Boolean enabled = true;
	/**
	 * 新增人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "add_user_id")
	private User addUser;
	/**
	 * 新增时间
	 */
	@Column(name = "add_time")
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
	private Date updateTime = new Date();
	/**
	 * 乐观锁
	 */
	@Version
	private Integer version;
	/**
	 * 角色
	 */
	@ManyToMany(mappedBy = "resources", targetEntity = Role.class, fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<Role>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}