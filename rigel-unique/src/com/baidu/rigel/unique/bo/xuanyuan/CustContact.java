package com.baidu.rigel.unique.bo.xuanyuan;

import java.util.ArrayList;
import java.util.List;


public class CustContact extends CustContactBase {
	// TODO Anders Zhu ： 重构
	/**
	 * 是否失效：0：否
	 */
	public static final Short DISABLED_N = 0;

	private List<Phone> phoneList = new ArrayList<Phone>();

	public List<Phone> getPhoneList() {
		return phoneList;
	}

	public void setPhoneList(List<Phone> phoneList) {
		this.phoneList = phoneList;
	}
}