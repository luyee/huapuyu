package com.anders.ssh.bo.test;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公司详细信息
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_company_info")
public class CompanyInfo implements Serializable {

	private static final long serialVersionUID = -4406529612440333530L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = true, length = 50)
	private String info;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
