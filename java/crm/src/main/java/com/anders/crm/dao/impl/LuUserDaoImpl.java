package com.anders.crm.dao.impl;

import org.springframework.stereotype.Repository;

import com.anders.crm.bo.LuUser;
import com.anders.crm.dao.LuUserDao;

//@Repository("userDao")
@Repository
public class LuUserDaoImpl extends GenericDaoImpl<Long, LuUser> implements LuUserDao {
}
