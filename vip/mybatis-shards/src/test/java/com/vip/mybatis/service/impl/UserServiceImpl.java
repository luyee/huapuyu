package com.vip.mybatis.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vip.mybatis.annotation.ShardParam;
import com.vip.mybatis.bo.User;
import com.vip.mybatis.dao.UserMapper;
import com.vip.mybatis.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public void save(@ShardParam(name = "user", field = "id") User user) {
		userMapper.insert(user);

		userMapper.updateByPrimaryKey(user);
	}

	@Override
	public void deleteById(@ShardParam(name = "user") Long id) {
		userMapper.deleteByPrimaryKey(id.intValue());

	}

	@Override
	public void update(@ShardParam(name = "user", field = "id") Map<String, Object> map) {
		User user = new User();
		user.setId((Integer) map.get("id"));
		user.setName(String.valueOf(map.get("name")));
		userMapper.updateByPrimaryKey(user);
	}
}
