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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_user")
@Searchable
@XmlRootElement
public class User extends BaseBO implements UserDetails {

	private static final long serialVersionUID = 6424414021596996848L;

	public static final String USERNAME = "username";

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SearchableId
	private Long id;
	/**
	 * 账号
	 */
	@Column(name = "user_name", nullable = false, length = 50, unique = true)
	@SearchableProperty
	private String username;
	/**
	 * 密码
	 */
	@Column(nullable = false, length = 50)
	@XmlTransient
	private String password;
	/**
	 * 姓名
	 */
	@Column(nullable = false, length = 50)
	@SearchableProperty
	private String name;
	/**
	 * 邮箱
	 */
	@Column(nullable = false, length = 50, unique = true)
	@SearchableProperty
	private String email;
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
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_user_to_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	/**
	 * 用户组
	 */
	@ManyToMany(targetEntity = UserGroup.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_user_to_user_group", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "user_group_id"))
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();
	/**
	 * 启用（0：启用；1：禁用）
	 */
	@Column(nullable = false)
	private boolean enabled = true;
	/**
	 * 账号过期（0：有效；1：过期）
	 */
	@Column(name = "account_expired", nullable = false)
	private boolean accountExpired = true;
	/**
	 * 账号锁定（0：有效；1：锁定）
	 */
	@Column(name = "account_locked", nullable = false)
	private boolean accountLocked = true;
	/**
	 * 凭证过期（0：有效；1：过期）
	 */
	@Column(name = "credentials_expired", nullable = false)
	private boolean credentialsExpired = true;

	@Transient
	private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

	// spring security 3

	public Set<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	public boolean isEnabled() {
		return enabled;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "hello world";
	}

}
