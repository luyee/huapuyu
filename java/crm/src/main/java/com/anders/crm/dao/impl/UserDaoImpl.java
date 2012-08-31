package com.anders.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.anders.crm.bo.User;
import com.anders.crm.dao.UserDao;

//@Repository("userDao")
@Repository
public class UserDaoImpl extends GenericDaoImpl<Long, User> implements UserDao {
	public String getNameByUsername(String username) {
		return findUnique("select user.name from User user where user.username = ?", new Object[] { username });
	}

	public boolean isExistByUsername(String username) {
		Long count = findUnique("select count(*) from User user where user.username = ?", new Object[] { username });
		if (count != null && count.equals(1L)) {
			return true;
		}
		return false;
	}
}
