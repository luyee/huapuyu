package com.anders.ssh.service;

import java.util.List;

import com.anders.ssh.model.annotation.Resource;

public interface ResourceService {
	public Resource getById(Long id);

	public void save(Resource resource);

	public void update(Resource resource);

	public void delete(Resource resource);

	public void deleteById(Long id);

	public List<Resource> getAll();

	public List<Resource> getByType(String type);
}