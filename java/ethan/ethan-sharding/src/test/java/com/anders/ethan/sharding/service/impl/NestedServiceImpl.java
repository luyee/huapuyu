package com.anders.ethan.sharding.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anders.ethan.sharding.service.NestedService;
import com.anders.ethan.sharding.service.UserService;

@Service("nestedService")
public class NestedServiceImpl implements NestedService {

	@Resource
	private UserService userService;

	@Override
	public void insert(Long id) {
		userService.findById(id);
		userService.find(id);
		userService.insert(id);
		userService.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Long findById(Long id) {
		return userService.findById(id);
	}
}
