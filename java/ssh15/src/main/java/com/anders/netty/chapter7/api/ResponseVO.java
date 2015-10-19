package com.anders.netty.chapter7.api;

import java.io.Serializable;

public class ResponseVO implements Serializable {

	private static final long serialVersionUID = 6398690335298318681L;

	private Long id;
	private Boolean ok;
	private String desc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
