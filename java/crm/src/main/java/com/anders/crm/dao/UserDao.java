package com.anders.crm.dao;

import com.anders.crm.bo.User;

public interface UserDao extends GenericDao<Long, User> {

	String getNameByUsername(String username);

	boolean isExistByUsername(String username);

	boolean isExistByEmail(String email);
}
