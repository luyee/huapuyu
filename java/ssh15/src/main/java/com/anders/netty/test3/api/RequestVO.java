package com.anders.netty.test3.api;

import java.io.Serializable;

public class RequestVO implements Serializable {

	private static final long serialVersionUID = 1250261178519899650L;

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
