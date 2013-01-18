package com.anders.ssh.dao.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.dao.Dao;

@Component("mybatisDataDao")
public class DataDao implements Dao<Long, Data> {

	@Resource
	private DataMapper dataMapper;

	@Override
	public void delete(Data data) {
		dataMapper.delete(data);

	}

	@Override
	public void deleteById(Long id) {
		dataMapper.deleteById(id);
	}

	@Override
	public List<Data> getAll() {
		return dataMapper.getAll();
	}

	@Override
	public Data getById(Long id) {
		return dataMapper.getById(id);
	}

	@Override
	public void save(Data data) {
		dataMapper.save(data);
	}

	@Override
	public void update(Data data) {
		dataMapper.update(data);
	}

	@Override
	public void saveOrUpdate(Data entity) {
		// TODO Auto-generated method stub

	}

}
