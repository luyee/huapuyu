package com.anders.ssh.dao.hibernate;

import org.springframework.stereotype.Component;

import com.anders.ssh.model.xml.User;

@Component("hibernateUserDao")
public class UserDao extends HibernateDao<Integer, User>
{
}
