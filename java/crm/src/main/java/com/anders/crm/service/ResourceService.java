package com.anders.crm.service;

import java.util.List;

import com.anders.crm.bo.Resource;
import com.anders.crm.dao.ResourceDAO;

public class ResourceService extends BaseService<Long, Resource> {

	private ResourceDAO resourceDAO;

	public List<Resource> getAll() {
		return resourceDAO.getAll();
	}

}
