package com.anders.vote.bo;

import com.anders.vote.mybatis.annotation.Id;
import com.anders.vote.mybatis.annotation.OptimisticLocking;
import com.anders.vote.mybatis.annotation.Version;

@OptimisticLocking("tb_tester")
public class Tester {
	@Id("id")
	private Long id;

	private String value;

	@Version("version")
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
