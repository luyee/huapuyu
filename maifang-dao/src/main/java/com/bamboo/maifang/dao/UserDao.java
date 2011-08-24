package com.bamboo.maifang.dao;

import org.springframework.stereotype.Repository;

import com.bamboo.maifang.model.User;

@Repository("userDao")
public class UserDao extends BaseDao<User, Long> {

}
