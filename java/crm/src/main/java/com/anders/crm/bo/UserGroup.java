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

import lombok.Getter;
import lombok.Setter;

/**
 * 用户组
 * 
 * @author Anders Zhu
 * 
 */
@Setter
@Getter
@Entity
@Table(name = "tb_user_group")
public class UserGroup extends BaseBO {

	private static final long serialVersionUID = 2963700485353847916L;

	/**
	 * 名称
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	/**
	 * 角色集合
	 */
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_user_group_to_role", joinColumns = @JoinColumn(name = "user_group_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	/**
	 * 用户集合
	 */
	@ManyToMany(mappedBy = "userGroups", targetEntity = User.class, fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<User>();

}
