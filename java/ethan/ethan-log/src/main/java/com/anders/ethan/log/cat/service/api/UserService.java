package com.anders.ethan.log.cat.service.api;

import com.anders.ethan.log.cat.entity.User;

public interface UserService {

	User getById(Long id);

	void save(User user);
}