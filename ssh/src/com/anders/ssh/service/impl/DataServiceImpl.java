package com.anders.ssh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.dao.hibernate.DataDao;
import com.anders.ssh.service.DataService;

@Service("dataService")
public class DataServiceImpl implements DataService {
	@Resource(name = "hibernateDataDao")
	private DataDao dataDao;

	@Override
	public void delete(Data data) {
		dataDao.delete(data);
	}

	@Override
	public void deleteById(Long id) {
		dataDao.deleteById(id);
	}

	@Override
	public Data getById(Long id) {
		return dataDao.getById(id);
	}

	@Override
	public List<Data> getAll() {
		return dataDao.getAll();
	}

	@Override
	public void save(Data data) {
		dataDao.save(data);
	}

	@Override
	public void update(Data data) {
		dataDao.update(data);
	}

	@Override
	public void saveOrUpdate(Data data) {
		dataDao.saveOrUpdate(data);
	}
}
