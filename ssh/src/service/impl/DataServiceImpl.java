package service.impl;

import java.util.List;

import model.Data;

import org.springframework.transaction.annotation.Transactional;

import service.interf.DataService;
import dao.hibernate.DataDao;

@Transactional
public class DataServiceImpl implements DataService
{
	private DataDao dao;

	public DataDao getDao()
	{
		return dao;
	}

	public void setDao(DataDao dao)
	{
		this.dao = dao;
	}

	@Override
	public void delete(Data entity)
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
	public Data getById(Integer id)
	{
		return dao.getById(id);
	}

	@Override
	public List<Data> getAll()
	{
		return dao.getAll();
	}

	@Override
	public void save(Data entity)
	{
		dao.save(entity);
	}

	@Override
	public void update(Data entity)
	{
		dao.update(entity);
	}
}
