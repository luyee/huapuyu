package com.anders.crm.dao.impl;

import org.springframework.stereotype.Component;

import com.anders.crm.bo.Resource;
import com.anders.crm.dao.ResourceDao;

@Component
public class ResourceDaoImpl extends GenericDaoImpl<Long, Resource> implements ResourceDao {
}
