package service.impl;

import java.util.List;

import model.Role;

import org.springframework.transaction.annotation.Transactional;

import service.interf.RoleService;
import dao.hibernate.RoleDao;

@Transactional
public class RoleServiceImpl implements RoleService
{
	private RoleDao dao;

	public RoleDao getDao()
	{
		return dao;
	}

	public void setDao(RoleDao dao)
	{
		this.dao = dao;
	}

	@Override
	public void delete(Role entity)
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
	public Role getById(Integer id)
	{
		return dao.getById(id);
	}

	@Override
	public List<Role> getAll()
	{
		return dao.getAll();
	}

	@Override
	public void save(Role entity)
	{
		dao.save(entity);
	}

	@Override
	public void update(Role entity)
	{
		dao.update(entity);
	}
}
