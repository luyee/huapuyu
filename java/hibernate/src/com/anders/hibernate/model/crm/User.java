package com.anders.hibernate.model.crm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 用户
 * 
 * @author Anders Zhu
 * 
 */
@Entity
@Table(name = "tb_user")
// @Analyzer(impl = MMSegAnalyzer.class)
@XmlRootElement
public class User extends BaseBO {

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
	private String name;
	/**
	 * 邮箱
	 */
	@Column(nullable = false, length = 50, unique = true)
	private String email;

}
