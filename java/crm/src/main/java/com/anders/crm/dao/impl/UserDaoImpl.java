package com.anders.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.anders.crm.bo.User;
import com.anders.crm.dao.UserDao;

//@Repository("userDao")
@Repository
public class UserDaoImpl extends GenericDaoImpl<Long, User> implements UserDao {
}
