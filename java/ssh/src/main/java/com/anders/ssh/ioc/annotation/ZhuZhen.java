package com.anders.ssh.ioc.annotation;

import org.springframework.stereotype.Component;

@Component
public class ZhuZhen implements IZhuZhen {
	private String name;
	private String relation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public ZhuZhen() {
		this.name = "朱振";
		this.relation = "儿子";
	}

	@Override
	public String toString() {
		return "姓名：" + this.name + "；关系:" + this.relation;
	}
}
