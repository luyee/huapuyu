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
public class Role implements Serializable {

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
	private Set<RoleToResource> roleToResource = Collections.emptySet();
	/**
	 * 用户和角色关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserToRole> userToRoleSet = Collections.emptySet();
	/**
	 * 用户组和角色关系
	 */
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserGroupToRole> userGroupToRoleSet = Collections.emptySet();

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

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Set<UserToRole> getUserToRoleSet() {
		return userToRoleSet;
	}

	public void setUserToRoleSet(Set<UserToRole> userToRoleSet) {
		this.userToRoleSet = userToRoleSet;
	}

	public Set<UserGroupToRole> getUserGroupToRoleSet() {
		return userGroupToRoleSet;
	}

	public void setUserGroupToRoleSet(Set<UserGroupToRole> userGroupToRoleSet) {
		this.userGroupToRoleSet = userGroupToRoleSet;
	}

	public Set<RoleToResource> getRoleToResource() {
		return roleToResource;
	}

	public void setRoleToResource(Set<RoleToResource> roleToResource) {
		this.roleToResource = roleToResource;
	}

}