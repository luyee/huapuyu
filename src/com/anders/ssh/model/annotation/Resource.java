package com.anders.ssh.model.annotation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 资源
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_resource")
public class Resource implements Serializable {
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
	 * 角色
	 */
	@ManyToMany(mappedBy = "resources", targetEntity = Role.class, fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<Role>(0);

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}