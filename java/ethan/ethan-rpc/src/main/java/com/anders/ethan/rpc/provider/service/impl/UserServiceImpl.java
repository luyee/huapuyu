package com.anders.ethan.rpc.provider.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.anders.ethan.rpc.api.entity.User;
import com.anders.ethan.rpc.api.service.UserService;
import com.anders.ethan.rpc.common.RpcService;

@Service("userService")
@RpcService(UserService.class)
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Override
	public void insert(User user) {
		LOGGER.debug("insert:\t" + user);
	}

	@Override
	public void delete(Long id) {
		LOGGER.debug("delete:\t" + id);
	}

	@Override
	public void update(User user) {
		LOGGER.debug("update:\t" + user);
	}

	@Override
	public User findById(Long id) {
		LOGGER.debug("findById:\t" + id);
		return new User(1L, "朱振", 33, "镇江", "huapuyu@qq.com");
	}

	@Override
	public List<User> find(User user) {
		LOGGER.debug("find:\t" + user);

		List<User> users = new ArrayList<User>();
		for (long i = 2; i < 1000002; i++) {
			users.add(new User(i, "朱翊莀", 1, "上海", "ethan@qq.com"));
			// users.add(new User(3L, "郭立立", 30, "镇江", "guolili@qq.com"));
		}

		return users;
	}

}
