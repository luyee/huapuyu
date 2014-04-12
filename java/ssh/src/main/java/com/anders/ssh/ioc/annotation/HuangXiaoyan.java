package com.anders.ssh.ioc.annotation;

import org.springframework.stereotype.Component;

@Component
public class HuangXiaoyan {
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

	public HuangXiaoyan() {
		this.name = "黄晓燕";
		this.relation = "老婆";
	}

	@Override
	public String toString() {
		return "姓名：" + this.name + "；关系:" + this.relation;
	}
}
