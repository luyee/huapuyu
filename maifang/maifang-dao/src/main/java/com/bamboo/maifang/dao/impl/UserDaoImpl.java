package com.bamboo.maifang.dao.impl;

import org.springframework.stereotype.Repository;

import com.bamboo.maifang.dao.BaseDaoSkeleton;
import com.bamboo.maifang.dao.UserDao;
import com.bamboo.maifang.model.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoSkeleton<User, Long> implements UserDao {

}
