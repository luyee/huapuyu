package com.anders.ethan.log.cat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.anders.ethan.log.cat.entity.User;
import com.anders.ethan.log.cat.mapper.UserMapper;
import com.anders.ethan.log.cat.service.api.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userMapper")
	private UserMapper userMapper;

	@Override
	@Transactional
	public User getById(Long id) {
		Assert.notNull(id);

		// Transaction transaction = Cat.newTransaction("Service", "sayHello");
		//
		// String result = "hello " + name + ", response form provider : "
		// + RpcContext.getContext().getLocalAddress() + ":"
		// + RpcContext.getContext().getLocalPort();
		//
		// transaction.setStatus(Transaction.SUCCESS);
		// transaction.complete();

		return userMapper.getById(id);
	}

	@Override
	public void save(User user) {
		Assert.notNull(user);

		userMapper.save(user);
	}
}