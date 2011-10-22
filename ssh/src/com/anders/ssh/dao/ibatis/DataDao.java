package com.anders.ssh.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Component;

import com.anders.ssh.model.xml.Data;

@Component("ibatisDataDao")
public class DataDao extends IbatisDao<Long, Data> {
	@Override
	public void delete(Data data) {
		getSqlMapClientTemplate().delete("delete", data);
	}

	@Override
	public Data getById(Long id) {
		return (Data) getSqlMapClientTemplate().queryForObject("getById", id);
	}

	@Override
	public void save(Data data) {
		getSqlMapClientTemplate().insert("save", data);
	}

	@Override
	public void update(Data data) {
		getSqlMapClientTemplate().update("update", data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Data> getAll() {
		return getSqlMapClientTemplate().queryForList("getAll");
	}

	@Override
	public void deleteById(Long id) {
		getSqlMapClientTemplate().delete("deleteById", id);
	}

}
