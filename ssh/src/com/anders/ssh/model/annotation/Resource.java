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
@Table(name = "tb_resource")
public class Resource implements Serializable
{
	private static final long serialVersionUID = 943640614451654490L;

	/**
	 * 资源编号（主键）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 资源名
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	/**
	 * 资源内容
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String content;
	/**
	 * 启用符（启用：true；禁用：false）
	 */
	@Column(nullable = false)
	private Boolean enable = true;
	/**
	 * 角色和资源关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "resource")
	// TODO Collections.emptySet()和new HashSet<RoleResourceRelation>(0)作用一样，创建一个size=0的HashSet
	// private Set<RoleResourceRelation> roleResourceRelationSet = new HashSet<RoleResourceRelation>(0);
	private Set<RoleResourceRelation> roleResourceRelationSet = Collections.emptySet();

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

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
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

	public static void main(String[] args)
	{
		Resource resource = new Resource();
		System.out.println(resource.getRoleResourceRelationSet().size());
	}
}