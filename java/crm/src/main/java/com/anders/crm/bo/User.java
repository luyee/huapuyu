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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * 用户
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_user")
@Indexed(index = "user")
//@Analyzer(impl = MMSegAnalyzer.class)
@XmlRootElement
public class User extends BaseBO {

	private static final long serialVersionUID = 6424414021596996848L;

	public static final String USERNAME = "username";
	public static final String EMAIL = "email";

	/**
	 * 账户名
	 */
	@Column(name = "user_name", nullable = false, length = 50, unique = true)
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
	@Field(name = "name", index = Index.YES, store = Store.YES, boost = @Boost(2f))
	@Boost(1.5f)
	private String name;
	/**
	 * 邮箱
	 */
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	/**
	 * 角色集合
	 */
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_user_to_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	/**
	 * 用户组集合
	 */
	@ManyToMany(targetEntity = UserGroup.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_user_to_user_group", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "user_group_id"))
	private Set<UserGroup> userGroups = new HashSet<UserGroup>();

	// getter and setter

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

}
