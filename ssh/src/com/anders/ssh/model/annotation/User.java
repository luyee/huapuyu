package com.anders.ssh.model.annotation;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.anders.ssh.common.Constant;

@Entity
@Table(name = "tb_user")
public class User implements UserDetails
{
	private static final long serialVersionUID = 4766918848108919655L;

	/**
	 * 用户编号（主键）
	 */
	@Id
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
	@Column(nullable = false)
	private Boolean enable = true;
	/**
	 * 用户和角色关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserRoleRelation> userRoleRelationSet = Collections.emptySet();
	/**
	 * 用户和用户组关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserUserGroupRelation> userUserGroupRelationSet = Collections.emptySet();

	// ************************************************************
	// 以下为UserDetails接口的实现方法，专门用于Spring Security
	// ************************************************************

	@Transient
	private Collection<GrantedAuthority> authorities;

	@Override
	public Collection<GrantedAuthority> getAuthorities()
	{
		return this.authorities;
	}

	@Override
	public String getPassword()
	{
		return this.password;
	}

	@Override
	public String getUsername()
	{
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return this.enable;
	}

	// ************************************************************
	// getters and setters
	// ************************************************************

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Set<UserRoleRelation> getUserRoleRelationSet()
	{
		return userRoleRelationSet;
	}

	public void setUserRoleRelationSet(Set<UserRoleRelation> userRoleRelationSet)
	{
		this.userRoleRelationSet = userRoleRelationSet;
	}

	public Set<UserUserGroupRelation> getUserUserGroupRelationSet()
	{
		return userUserGroupRelationSet;
	}

	public void setUserUserGroupRelationSet(Set<UserUserGroupRelation> userUserGroupRelationSet)
	{
		this.userUserGroupRelationSet = userUserGroupRelationSet;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}
}
