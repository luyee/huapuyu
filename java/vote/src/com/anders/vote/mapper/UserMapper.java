package com.anders.vote.mapper;

import com.anders.vote.bo.User;

public interface UserMapper extends GenericMapper<Long, User> {
	User getByUserName(String userName);
}
