package com.anders.ethan.rpc.api;

import java.util.List;

public interface UserService {
	void insert(User user);

	void delete(Long id);

	void update(User user);

	User findById(Long id);

	List<User> find(User user);
}
