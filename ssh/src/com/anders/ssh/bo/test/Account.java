package com.anders.ssh.bo.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 客户
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_account")
public class Account implements Serializable {

	private static final long serialVersionUID = 7677834094856155053L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = true, length = 50)
	private String name;

	@Column(name = "enable", nullable = false)
	private Boolean enable = true;

	@ManyToMany(mappedBy = "accounts", targetEntity = Company.class)
	private List<Company> companies = new ArrayList<Company>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
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

}
