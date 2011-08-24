package com.anders.ssh.dao.ibatis;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.anders.ssh.dao.Dao;
import com.anders.ssh.model.xml.Data;
import com.ibatis.sqlmap.client.SqlMapClient;


//@Component(value = "ibatisDataDao")
@Component
public class DataDao extends SqlMapClientDaoSupport implements Dao<Integer, Data>
{
	// 增加setSqlMapClientMocker方法，避免在XML文件中给DAO方法注入SqlMapClient。
	@Resource
	public void setSqlMapClientMocker(SqlMapClient sqlMapClient)
	{
		super.setSqlMapClient(sqlMapClient);
	}

	@Override
	public void delete(Data entity)
	{
		getSqlMapClientTemplate().delete("dataDelete", entity);
	}

	@Override
	public Data getById(Integer id)
	{
		return (Data) getSqlMapClientTemplate().queryForObject("dataGetById", id);
	}

	@Override
	public void save(Data entity)
	{
		getSqlMapClientTemplate().insert("dataSave", entity);
	}

	@Override
	public void update(Data entity)
	{
		getSqlMapClientTemplate().update("dataUpdate", entity);
	}

	@Override
	public List<Data> getAll()
	{
		return getSqlMapClientTemplate().queryForList("dataGetAll");
	}

	@Override
	public void deleteById(Integer id)
	{
		getSqlMapClientTemplate().delete("dataDeleteById", id);
	}

}
