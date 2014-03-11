package com.anders.experiment.spring.impl;

import com.anders.experiment.spring.User;
import com.anders.experiment.spring.UserService;

public class UserServiceImpl implements UserService {
	private User user;
	private String name;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
