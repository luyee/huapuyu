package com.banboo.maifang.service.login.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banboo.maifang.dao.UserDao;
import com.banboo.maifang.model.User;
import com.banboo.maifang.service.login.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	
	private UserDao userDao;
	
	public List<User> getAllUser() {
		userDao.save(new User());
		return userDao.getAll();
	}
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
