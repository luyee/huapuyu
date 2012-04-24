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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.security.core.GrantedAuthority;

/**
 * 角色
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_role")
// @NamedQueries({ @NamedQuery(name = "findRoleByName", query = "select r from Role r where r.name = :name ") })
public class Role extends BaseBO implements GrantedAuthority {

	private static final long serialVersionUID = 3690197650654049848L;

	/**
	 * 主键.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 角色名
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	/**
	 * 启用（0：启用；1：禁用）
	 */
	@Column(nullable = false)
	private boolean enabled = true;
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
	 * 资源
	 */
	@ManyToMany(targetEntity = Resource.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_role_to_resource", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
	private Set<Resource> resources = new HashSet<Resource>();
	/**
	 * 用户
	 */
	@ManyToMany(mappedBy = "roles", targetEntity = User.class, fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<User>();
	/**
	 * 用户组
	 */
	@ManyToMany(mappedBy = "roles", targetEntity = UserGroup.class, fetch = FetchType.LAZY)
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();

	// spring security 3

	public String getAuthority() {
		return name;
	}

	// getter and setter

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

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

}
