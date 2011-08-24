package com.anders.ssh.model.annotation;

import java.io.Serializable;
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

@Entity
@Table(name = "tb_role")
public class Role implements Serializable
{

	private static final long serialVersionUID = -3030596606244316907L;

	/**
	 * 角色编号（主键）
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
	 * 启用符（启用：true；禁用：false）
	 */
	@Column(nullable = false)
	private Boolean enable = true;
	/**
	 * 角色和资源关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<RoleResourceRelation> roleResourceRelationSet = Collections.emptySet();
	/**
	 * 用户和角色关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserRoleRelation> userRoleRelationSet = Collections.emptySet();
	/**
	 * 用户组和角色关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserGroupRoleRelation> userGroupRoleRelationSet = Collections.emptySet();

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean getEnable()
	{
		return enable;
	}

	public void setEnable(Boolean enable)
	{
		this.enable = enable;
	}

	public Set<RoleResourceRelation> getRoleResourceRelationSet()
	{
		return roleResourceRelationSet;
	}

	public void setRoleResourceRelationSet(Set<RoleResourceRelation> roleResourceRelationSet)
	{
		this.roleResourceRelationSet = roleResourceRelationSet;
	}

	public Set<UserRoleRelation> getUserRoleRelationSet()
	{
		return userRoleRelationSet;
	}

	public void setUserRoleRelationSet(Set<UserRoleRelation> userRoleRelationSet)
	{
		this.userRoleRelationSet = userRoleRelationSet;
	}

	public Set<UserGroupRoleRelation> getUserGroupRoleRelationSet()
	{
		return userGroupRoleRelationSet;
	}

	public void setUserGroupRoleRelationSet(Set<UserGroupRoleRelation> userGroupRoleRelationSet)
	{
		this.userGroupRoleRelationSet = userGroupRoleRelationSet;
	}

}