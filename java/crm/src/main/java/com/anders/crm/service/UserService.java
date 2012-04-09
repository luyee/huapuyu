package com.anders.crm.service;

import java.util.List;

import com.anders.crm.bo.User;

public interface UserService extends GenericService<Long, User> {

	public User getByUsername(String username);

	public List<String> getRoleNameListByUsername(String username);
}
