package com.anders.ssh.dao.hibernate;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.annotation.Resource;

@Component("hibernateResourceDao")
public class ResourceDao extends HibernateDao<Long, Resource> {
}
