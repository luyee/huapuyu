package com.anders.ssh.dao.hibernate;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.xml.Role;

@Component("hibernateRoleDao")
public class RoleDao extends HibernateDao<Long, Role> {
}
