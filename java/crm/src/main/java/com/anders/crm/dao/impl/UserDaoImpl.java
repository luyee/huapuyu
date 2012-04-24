package com.anders.crm.dao.impl;

import org.springframework.stereotype.Component;

import com.anders.crm.bo.User;
import com.anders.crm.dao.UserDao;

@Component
public class UserDaoImpl extends GenericDaoImpl<Long, User> implements UserDao {
}
