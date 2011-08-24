package com.bamboo.maifang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bamboo.maifang.api.login.LoginService;
import com.bamboo.maifang.dao.UserDao;
import com.bamboo.maifang.model.User;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserDao userDao;
	
	public List<User> getAllUser() {
		userDao.save(new User());
		return userDao.getAll();
	}

}
