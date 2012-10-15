package com.anders.ssh.model.annotation;

import java.util.ArrayList;
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

import org.springframework.security.core.GrantedAuthority;

/**
 * 角色
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_role")
// TODO Anders Zhu : may be delete comments
// @NamedQueries({ @NamedQuery(name = "findRoleByName", query = "select r from Role r where r.name = :name ") })
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = -2204007491091024923L;
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
	 * 资源
	 */
	@ManyToMany(targetEntity = Resource.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_role_to_resource", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
	private List<Resource> resources = new ArrayList<Resource>(0);
	/**
	 * 用户
	 */
	@ManyToMany(mappedBy = "roles", targetEntity = User.class, fetch = FetchType.LAZY)
	private List<User> users = new ArrayList<User>(0);
	/**
	 * 用户组
	 */
	@ManyToMany(mappedBy = "roles", targetEntity = UserGroup.class, fetch = FetchType.LAZY)
	private List<UserGroup> userGroups = new ArrayList<UserGroup>(0);

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

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	// Spring Security
	// TODO Anders Zhu : 重构
	@Override
	public String getAuthority() {
		return null;
	}
}