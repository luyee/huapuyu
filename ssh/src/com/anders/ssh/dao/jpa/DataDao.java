package com.anders.ssh.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Component;

import com.anders.ssh.model.xml.Data;

@Component("jpaDataDao")
public class DataDao extends JpaDao<Integer, Data>
{
	@Override
	public void delete(Data entity)
	{
		getJpaTemplate().remove(entity);
	}

	@Override
	public void deleteById(Integer id)
	{
		getJpaTemplate().remove(getJpaTemplate().getReference(Data.class, id));
	}

	@Override
	// @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Data getById(Integer id)
	{
		return getJpaTemplate().find(Data.class, id);
	}

	@Override
	// @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Data> getAll()
	{
		return getJpaTemplate().find("select data from Data data");
	}

	@Override
	public void save(Data entity)
	{
		getJpaTemplate().persist(entity);
	}

	@Override
	public void update(Data entity)
	{
		getJpaTemplate().merge(entity);
	}
}
