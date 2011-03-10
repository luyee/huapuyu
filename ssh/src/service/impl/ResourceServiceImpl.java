package service.impl;

import java.util.List;

import model.Resource;

import org.springframework.transaction.annotation.Transactional;

import service.interf.ResourceService;
import dao.hibernate.ResourceDao;

@Transactional
public class ResourceServiceImpl implements ResourceService
{
	private ResourceDao dao;

	public ResourceDao getDao()
	{
		return dao;
	}

	public void setDao(ResourceDao dao)
	{
		this.dao = dao;
	}

	@Override
	public void delete(Resource entity)
	{
		dao.delete(entity);
	}

	@Override
	public void deleteById(Integer id)
	{
		dao.deleteById(id);
	}

	@Override
	@Transactional()
	public Resource getById(Integer id)
	{
		return dao.getById(id);
	}

	@Override
	public List<Resource> getAll()
	{
		return dao.getAll();
	}

	@Override
	public void save(Resource entity)
	{
		dao.save(entity);
	}

	@Override
	public void update(Resource entity)
	{
		dao.update(entity);
	}

	@Override
	public List<Resource> getByType(String type)
	{
		List<Resource> resourceList = dao.find("FROM Resource resource WHERE resource.type = ? AND resource.enable = true", type);
		if (resourceList.isEmpty())
		{
			throw new RuntimeException("resourceList is empty!");
		}
		return resourceList;

	}
}
