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
@Table(name = "tb_user_group")
public class UserGroup implements Serializable
{
	private static final long serialVersionUID = 1260103790785321166L;

	/**
	 * 用户组编号（主键）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 用户名
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	/**
	 * 启用符（启用：true；禁用：false）
	 */
	@Column(nullable = false)
	private Boolean enable = true;
	/**
	 * 用户组和角色关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "userGroup")
	private Set<UserGroupRoleRelation> userGroupRoleRelationSet = Collections.emptySet();
	/**
	 * 用户和用户组关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "userGroup")
	private Set<UserUserGroupRelation> userUserGroupRelationSet = Collections.emptySet();

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

	public Set<UserGroupRoleRelation> getUserGroupRoleRelationSet()
	{
		return userGroupRoleRelationSet;
	}

	public void setUserGroupRoleRelationSet(Set<UserGroupRoleRelation> userGroupRoleRelationSet)
	{
		this.userGroupRoleRelationSet = userGroupRoleRelationSet;
	}

	public Set<UserUserGroupRelation> getUserUserGroupRelationSet()
	{
		return userUserGroupRelationSet;
	}

	public void setUserUserGroupRelationSet(Set<UserUserGroupRelation> userUserGroupRelationSet)
	{
		this.userUserGroupRelationSet = userUserGroupRelationSet;
	}

}
