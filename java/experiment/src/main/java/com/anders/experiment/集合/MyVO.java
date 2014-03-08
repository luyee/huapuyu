package com.anders.experiment.集合;

import java.util.ArrayList;
import java.util.List;

public class MyVO {
	private List<String> list;

	public MyVO() {
		list = new ArrayList<String>();
		list.add("张三");
		list.add("李四");
		list.add("王五");
		list.add("赵六");
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
}
