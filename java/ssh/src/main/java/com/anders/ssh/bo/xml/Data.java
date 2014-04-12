package com.anders.ssh.bo.xml;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@Deprecated
public class Data implements Serializable {

	private static final long serialVersionUID = -3076049534246559365L;

	private Long id;
	private String name;
	private Byte type;
	private Boolean enable = true;
	private Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

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

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
