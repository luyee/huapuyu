package com.anders.crm.service.impl;

import java.util.List;

import com.anders.crm.bo.User;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.UserDao;
import com.anders.crm.service.UserService;

public class UserServiceImpl extends GenericServiceImpl<Long, User> implements UserService {

	private UserDao userDao;

	@Override
	public GenericDao<Long, User> getDao() {
		return userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User getByUsername(String username) {
		return getDao().findUniqueBy(User.USERNAME, username);
	}

	public List<String> getRoleNamesByUsername(String username) {
		return getDao().find("select role.name name from User user, Role role where user.roles.id = role.id and user.username = ?", username);
	}
}
