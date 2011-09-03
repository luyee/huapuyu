package com.anders.ssh.dao.hibernate;

import org.springframework.stereotype.Component;

import com.anders.ssh.model.xml.UserGroup;

@Component("hibernateUserGroupDao")
public class UserGroupDao extends HibernateDao<Integer, UserGroup>
{
}
