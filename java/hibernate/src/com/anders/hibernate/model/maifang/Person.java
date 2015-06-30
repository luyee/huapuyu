package com.anders.hibernate.model.maifang;

import java.io.Serializable;

/**
 * @hibernate.mapping default-lazy="false"
 * @hibernate.class table="t_person"
 * @hibernate.comment "人"
 */
public class Person implements Serializable {
	private static final long serialVersionUID = 6422096732289758030L;
	private Long id;// 标识
	private String name;// 名字
	private Integer age;// 年龄

	/**
	 * @hibernate.id generator-class="native"
	 * @hibernate.column name="ID" comment="标识"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="string" length="50" not-null="true"
	 * @hibernate.column name="MZ"comment="名字"
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property type="integer" not-null="true"
	 * @hibernate.column name="NL" comment="年龄"
	 */
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
