package com.anders.ssh.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anders.ssh.bo.annotation.Resource;
import com.anders.ssh.dao.hibernate.ResourceDao;
import com.anders.ssh.service.ResourceService;

@Component
@Transactional
public class ResourceServiceImpl implements ResourceService {
	@javax.annotation.Resource(name = "hibernateResourceDao")
	private ResourceDao resourceDao;

	@Override
	public void delete(Resource resource) {
		resourceDao.delete(resource);
	}

	@Override
	public void deleteById(Long id) {
		resourceDao.deleteById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Resource getById(Long id) {
		return resourceDao.getById(id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Resource> getAll() {
		return resourceDao.getAll();
	}

	@Override
	public void save(Resource resource) {
		resourceDao.save(resource);
	}

	@Override
	public void update(Resource resource) {
		resourceDao.update(resource);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Resource> getByType(String type) {
		List<Resource> resourceList = resourceDao.find("FROM Resource resource WHERE resource.type = ? AND resource.enable = true", type);
		if (CollectionUtils.isEmpty(resourceList))
			throw new RuntimeException("resourceList is empty");
		return resourceList;

	}
}
