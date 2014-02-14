package com.anders.crm.bo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Role extends BaseBO implements GrantedAuthority {

	private static final long serialVersionUID = 3690197650654049848L;

	public static final String ENABLED = "enabled";
	public static final String NAME = "name";

	/**
	 * 名称
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	/**
	 * 标签
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String role;
	/**
	 * 资源集合
	 */
	@ManyToMany(targetEntity = Url.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_role_to_url", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "url_id"))
	private Set<Url> urls = new HashSet<Url>();
	/**
	 * 用户集合
	 */
	@ManyToMany(mappedBy = "roles", targetEntity = User.class, fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<User>();
	/**
	 * 用户组集合
	 */
	@ManyToMany(mappedBy = "roles", targetEntity = UserGroup.class, fetch = FetchType.LAZY)
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();

	// Spring Security 3

	public String getAuthority() {
		return name;
	}

	// getter and setter

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Url> getUrls() {
		return urls;
	}

	public void setUrls(Set<Url> urls) {
		this.urls = urls;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
