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

import lombok.Getter;
import lombok.Setter;

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
@Setter
@Getter
@Entity
@Table(name = "tb_user")
@Indexed(index = "user")
// @Analyzer(impl = MMSegAnalyzer.class)
@XmlRootElement
public class User extends BaseBO {

	private static final long serialVersionUID = 6424414021596996848L;

	public static final String USERNAME = "username";
	public static final String EMAIL = "email";

	/**
	 * 用户名
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
}
