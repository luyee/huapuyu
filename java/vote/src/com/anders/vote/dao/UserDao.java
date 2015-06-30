package com.anders.vote.dao;

import com.anders.vote.bo.User;

public interface UserDao extends GenericDao<Long, User> {
	User getByUserName(String userName);
}
