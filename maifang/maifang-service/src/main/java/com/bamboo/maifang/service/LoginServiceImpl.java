package com.bamboo.maifang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bamboo.maifang.api.login.LoginService;
import com.bamboo.maifang.dao.UserDao;
import com.bamboo.maifang.model.User;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserDao userDao;
	
	@Transactional
	public List<User> getAllUser() {
		User u = new User();
		u.setPhoneNum("15221291301");
		userDao.saveNotUpdate(u);
		return userDao.getAll();
	}

}
