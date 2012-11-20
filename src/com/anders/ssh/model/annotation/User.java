package com.anders.ssh.model.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.anders.ssh.common.Constant;

/**
 * 用户
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {
	private static final long serialVersionUID = 4766918848108919655L;

	/**
	 * 用户编号（主键）
	 */
	@Id
	// TODO Anders Zhu : unitils中的dbunit报错Caused by: org.dbunit.dataset.NoSuchColumnException: tb_user.NAME - (Non-uppercase input column: NAME) in ColumnNameToIndexes cache map. Note that the map's column names are NOT case sensitive.
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 用户名
	 */
	@Column(name = "user_name", length = 50, nullable = false, unique = true)
	private String userName;
	/**
	 * 用户姓名
	 */
	@Column(name = "name", length = 50, nullable = false)
	private String name;
	/**
	 * 用户密码
	 */
	@Column(name = "password", length = 50, nullable = false)
	private String password = Constant.DEFAULT_PWD;
	/**
	 * 启用符（启用：true；禁用：false）
	 */
	@Column(name = "enable", nullable = false)
	private Boolean enable = true;
	/**
	 * 角色
	 */
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_user_to_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<Role>(0);
	/**
	 * 用户组
	 */
	@ManyToMany(targetEntity = UserGroup.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_user_to_user_group", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "user_group_id"))
	private List<UserGroup> userGroups = new ArrayList<UserGroup>(0);

	// ************************************************************
	// 以下为UserDetails接口的实现方法，专门用于Spring Security
	// ************************************************************

	@Transient
	private Collection<GrantedAuthority> authorities;

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enable;
	}

	// ************************************************************
	// getters and setters
	// ************************************************************

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
}
