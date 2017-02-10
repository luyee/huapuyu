package com.anders.vote.action;

import org.apache.struts2.json.annotations.JSON;

import com.anders.vote.bo.User;

public class JsonAction extends BaseAction {

	private static final long serialVersionUID = -3013656302209804787L;

	private User user;

	public String json1() {
		user = new User();
		user.setEmail("huapuyu@ahoo.com.cn");
		return SUCCESS;
	}

	public String json2() {
		user = new User();
		user.setName("zhuzhen");
		user.setId(111L);
		return SUCCESS;
	}

	// getter and setter

	@JSON
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
