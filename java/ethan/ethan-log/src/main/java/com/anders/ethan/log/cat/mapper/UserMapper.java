package com.anders.ethan.log.cat.mapper;

import org.springframework.stereotype.Repository;

import com.anders.ethan.log.cat.entity.User;

@Repository
public interface UserMapper {

	void save(User user);

	User getById(Long id);
}
