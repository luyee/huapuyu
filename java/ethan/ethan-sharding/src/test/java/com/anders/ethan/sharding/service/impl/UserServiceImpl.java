package com.anders.ethan.sharding.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anders.ethan.sharding.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Override
	public void insert(Long id) {
	}

	@Transactional(readOnly = true)
	@Override
	public Long findById(Long id) {
		return 1L;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Long find(Long id) {
		return null;
	}
}
