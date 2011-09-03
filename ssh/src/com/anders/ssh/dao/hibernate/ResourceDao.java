package com.anders.ssh.dao.hibernate;

import org.springframework.stereotype.Component;

import com.anders.ssh.model.xml.Resource;

@Component("hibernateResourceDao")
public class ResourceDao extends HibernateDao<Integer, Resource>
{
}
