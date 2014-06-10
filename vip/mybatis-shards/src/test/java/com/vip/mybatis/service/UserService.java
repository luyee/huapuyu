package com.vip.mybatis.service;

import java.util.Map;

import com.vip.mybatis.bo.User;

public interface UserService {
	void save(User user);

	void deleteById(Long id);

	void update(Map<String, Object> map);
}
