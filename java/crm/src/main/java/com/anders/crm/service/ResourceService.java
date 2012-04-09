package com.anders.crm.service;

import java.util.List;

import com.anders.crm.bo.Resource;

public interface ResourceService extends GenericService<Long, Resource> {

	public List<Resource> getAll();
}
