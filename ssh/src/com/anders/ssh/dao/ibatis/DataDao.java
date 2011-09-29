package com.anders.ssh.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Component;

import com.anders.ssh.model.xml.Data;

@Component("ibatisDataDao")
public class DataDao extends IbatisDao<Integer, Data> {
	@Override
	public void delete(Data entity) {
		getSqlMapClientTemplate().delete("dataDelete", entity);
	}

	@Override
	public Data getById(Integer id) {
		return (Data) getSqlMapClientTemplate().queryForObject("dataGetById", id);
	}

	@Override
	public void save(Data entity) {
		getSqlMapClientTemplate().insert("dataSave", entity);
	}

	@Override
	public void update(Data entity) {
		getSqlMapClientTemplate().update("dataUpdate", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getAll() {
		return getSqlMapClientTemplate().queryForList("dataGetAll");
	}

	@Override
	public void deleteById(Integer id) {
		getSqlMapClientTemplate().delete("dataDeleteById", id);
	}

}
