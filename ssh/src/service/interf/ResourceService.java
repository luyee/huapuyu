package service.interf;

import java.util.List;

import model.Resource;

public interface ResourceService
{
	public Resource getById(Integer id);

	public void save(Resource entity);

	public void update(Resource entity);

	public void delete(Resource entity);

	public void deleteById(Integer id);

	public List<Resource> getAll();

	public List<Resource> getByType(String type);
}