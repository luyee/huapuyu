package com.anders.vote.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anders.vote.bo.User;
import com.anders.vote.dao.GenericDao;
import com.anders.vote.dao.UserDao;
import com.anders.vote.service.UserService;

@Service
public class UserServiceImpl extends GenericServiceImpl<Long, User> implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public GenericDao<Long, User> getDao() {
		return userDao;
	}

	@Override
	public User getByUserName(String userName) {
		if (StringUtils.isBlank(userName)) {
			throw new IllegalArgumentException("userName is blank");
		}
		return userDao.getByUserName(userName);
	}

}
