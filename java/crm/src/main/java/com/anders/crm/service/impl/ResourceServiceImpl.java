package com.anders.crm.service.impl;

import java.util.List;

import com.anders.crm.bo.Resource;
import com.anders.crm.dao.GenericDao;
import com.anders.crm.dao.ResourceDao;
import com.anders.crm.service.ResourceService;

public class ResourceServiceImpl extends GenericServiceImpl<Long, Resource> implements ResourceService {

	private ResourceDao resourceDao;

	public List<Resource> getAll() {
		return getDao().getAll();
	}

	@Override
	public GenericDao<Long, Resource> getDao() {
		return resourceDao;
	}

	public ResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
}
