package com.anders.crm.service;

import com.anders.crm.bo.User;

public interface UserService extends GenericService<Long, User> {

	User getUserByUsername(String username);

	String getNameByUsername(String username);

	void updatePasswordToDefault(String username);

	boolean isExistByUsername(String username);

}
