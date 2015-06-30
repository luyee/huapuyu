package com.anders.crm.dao;

import java.util.List;

import com.anders.crm.bo.User;

public interface UserDao extends GenericDao<Long, User> {

	String getNameByUsername(String username);

	boolean isExistByUsername(String username);

	boolean isExistByEmail(String email);

	List<String> getUsernameById(Long id);
}
