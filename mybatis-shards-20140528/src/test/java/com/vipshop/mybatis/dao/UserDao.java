package com.vipshop.mybatis.dao;

import com.vipshop.mybatis.bo.User;

public interface UserDao {
	public boolean insert(User user);

	public void update(User user);
}
