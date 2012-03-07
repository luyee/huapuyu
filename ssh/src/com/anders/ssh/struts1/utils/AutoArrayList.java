package com.anders.ssh.struts1.utils;

import java.util.ArrayList;

public class AutoArrayList<T> extends ArrayList<T> {

	private static final long serialVersionUID = -1082809942141852795L;

	private Class<T> itemClass;

	public AutoArrayList(Class<T> itemClass) {
		this.itemClass = itemClass;
	}

	public T get(int index) {
		try {
			while (index >= size()) {
				add(itemClass.newInstance());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return super.get(index);
	}
}
