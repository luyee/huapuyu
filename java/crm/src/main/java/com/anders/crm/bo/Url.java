package com.anders.crm.bo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * URL
 * 
 * @author Anders Zhu
 * 
 */
@Setter
@Getter
@Entity
@Table(name = "tb_url")
public class Url extends BaseBO {

	private static final long serialVersionUID = 2414932009925142715L;

	/**
	 * 名称
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String name;
	/**
	 * URL
	 */
	@Column(length = 50, nullable = false, unique = true)
	private String url;
	/**
	 * 角色集合
	 */
	@ManyToMany(mappedBy = "urls", targetEntity = Role.class, fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<Role>();

}