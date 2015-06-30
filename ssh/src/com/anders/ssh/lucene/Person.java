package com.anders.ssh.lucene;

import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;

@Searchable
public class Person {
	@SearchableId
	private int id;
	// @SearchableProperty(name="name") --- 这个默认的是分词，存储
	@SearchableProperty(name = "name")
	private String name;
	// 不分词，用下面的annotation定义
	@SearchableProperty(index = Index.UN_TOKENIZED, store = Store.YES)
	private String sex;
	@SearchableProperty(name = "adress")
	private String address;
	@SearchableProperty(index = Index.UN_TOKENIZED, store = Store.YES)
	private String duty;
	@SearchableProperty(index = Index.UN_TOKENIZED, store = Store.YES)
	private String phone;
	@SearchableProperty(name = "description")
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}