package com.vip.mybatis.dao;

import com.vip.mybatis.bo.User;

public interface UserDao {
	public boolean insert(User user);

	public void update(User user);
}
