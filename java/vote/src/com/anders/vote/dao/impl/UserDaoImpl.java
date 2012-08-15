package com.anders.vote.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.anders.vote.bo.User;
import com.anders.vote.dao.UserDao;
import com.anders.vote.mapper.GenericMapper;
import com.anders.vote.mapper.UserMapper;

@Repository
public class UserDaoImpl extends GenericDaoImpl<Long, User> implements UserDao {

	@Autowired
	private UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public GenericMapper<Long, User> getMapper() {
		return userMapper;
	}

}
