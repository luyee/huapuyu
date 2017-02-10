package com.anders.vote.service;

import com.anders.vote.bo.User;

public interface UserService extends GenericService<Long, User> {

	User getByUserName(String userName);
}
