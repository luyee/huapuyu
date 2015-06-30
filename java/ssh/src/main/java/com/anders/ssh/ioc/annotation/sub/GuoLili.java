package com.anders.ssh.ioc.annotation.sub;

import org.springframework.stereotype.Component;

// 如果使用@Component，不手工命名为subGuoLili，Spring会报错，找到两个GuoLili
@Component(value = "subGuoLili")
public class GuoLili {
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

	public GuoLili() {
		this.name = "郭立立";
		this.relation = "媳妇";
	}

	@Override
	public String toString() {
		return "[子包]姓名：" + this.name + "；关系:" + this.relation;
	}
}
