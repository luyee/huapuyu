package com.baidu.rigel.unique.bo.xuanyuan;

import java.util.ArrayList;
import java.util.List;

public class Customer extends CustomerBase {
	// TODO Anders Zhu : 下面的获取关联表的方法需要重构
	private List contactList = new ArrayList();
	private List urlList = new ArrayList();

	public List getContactList() {
		return contactList;
	}

	public void setContactList(List contactList) {
		this.contactList = contactList;
	}

	public List getUrlList() {
		return urlList;
	}

	public void setUrlList(List urlList) {
		this.urlList = urlList;
	}
}