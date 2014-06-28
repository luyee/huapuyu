package com.anders.crm.service;

import com.anders.crm.bo.User;

public interface UserService extends GenericService<Long, User> {

	User getUserByUsername(String username);

	String getNameByUsername(String username);

	void updatePasswordToDefault(String locale, String username, String from, String subject);

	boolean isExistByUsername(String username);

	boolean isExistByEmail(String email);

	void saveIndividual(User user);
}
