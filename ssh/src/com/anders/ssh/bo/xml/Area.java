package com.anders.ssh.bo.xml;

import java.io.Serializable;
import java.util.Set;

@Deprecated
public class Area implements Serializable {
	private static final long serialVersionUID = -31200209980445216L;

	private Long id;
	private String name;
	private Byte type;
	private Boolean enable;
	private Area parentArea;
	private Set<Area> sonAreas;

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

	public Area getParentArea() {
		return parentArea;
	}

	public void setParentArea(Area parentArea) {
		this.parentArea = parentArea;
	}

	public Set<Area> getSonAreas() {
		return sonAreas;
	}

	public void setSonAreas(Set<Area> sonAreas) {
		this.sonAreas = sonAreas;
	}
}
