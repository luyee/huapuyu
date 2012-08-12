package com.anders.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anders.crm.bo.User;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.UserDao;
import com.anders.crm.service.UserService;

//@Service("userService")
@Service
@Transactional
public class UserServiceImpl extends GenericServiceImpl<Long, User> implements UserService {

	@Autowired
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

	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		return getDao().findUniqueBy(User.USERNAME, username);
	}
}
