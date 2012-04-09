package com.anders.crm.service;

import java.util.List;

import com.anders.crm.bo.User;
import com.anders.crm.dao.UserDAO;

public class UserService extends BaseService<Long, User> {

	private UserDAO userDAO;

	public User getByUsername(String username) {
		return userDAO.findUniqueBy(User.USERNAME, username);
	}

	public List<String> getRoleNameListByUsername(String username) {
		return null;
	}
}
