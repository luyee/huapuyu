package com.anders.ethan.rpc.api.service;

import java.util.List;

import com.anders.ethan.rpc.api.entity.User;

public interface UserService {
	void insert(User user);

	void delete(Long id);

	void update(User user);

	User findById(Long id);

	List<User> find(User user);
}
