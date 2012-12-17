package com.anders.ssh.dao.hibernate;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.annotation.UserGroup;

@Component("hibernateUserGroupDao")
public class UserGroupDao extends HibernateDao<Long, UserGroup> {
}
