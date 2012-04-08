package com.anders.crm.bo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "tb_user_group")
public class UserGroup extends BaseBO {

	private static final long serialVersionUID = 2963700485353847916L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 用户组名
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	/**
	 * 启用（0：启用；1：禁用）
	 */
	@Column(nullable = false)
	private Boolean enabled = true;
	/**
	 * 新增人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "add_user_id")
	private User addUser;
	/**
	 * 新增时间
	 */
	@Column(name = "add_time")
	private Date addTime = new Date();
	/**
	 * 更新人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "update_user_id")
	private User updateUser;
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime = new Date();
	/**
	 * 乐观锁
	 */
	@Version
	private Integer version;
	/**
	 * 角色
	 */
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
	@JoinTable(name = "rlt_user_group_to_role", joinColumns = @JoinColumn(name = "user_group_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	/**
	 * 用户
	 */
	@ManyToMany(mappedBy = "userGroups", targetEntity = User.class, fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<User>();

}
