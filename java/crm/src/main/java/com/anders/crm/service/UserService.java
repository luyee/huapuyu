package com.anders.crm.service;

import com.anders.crm.bo.User;

public interface UserService extends GenericService<Long, User> {

	public User getByUsername(String username);
}
