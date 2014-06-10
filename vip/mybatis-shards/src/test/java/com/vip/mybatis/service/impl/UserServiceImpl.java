package com.vip.mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vip.mybatis.annotation.Shard;
import com.vip.mybatis.bo.User;
import com.vip.mybatis.dao.UserMapper;
import com.vip.mybatis.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Shard(name = "shard_user", classType = User.class, fieldType = Long.class, fieldName = "id")
	@Override
	public void save(User user) {
		userMapper.insert(user);
	}
}
