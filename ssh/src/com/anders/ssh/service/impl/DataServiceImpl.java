package com.anders.ssh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.anders.ssh.dao.jpa.DataDao;
import com.anders.ssh.model.xml.Data;
import com.anders.ssh.service.DataService;

@Component
public class DataServiceImpl implements DataService
{
	@Resource(name = "jpaDataDao")
	private DataDao dao;

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
