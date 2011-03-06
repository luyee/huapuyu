package dao.jpa;

import java.util.List;

import model.Test;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.interf.TestDao;

@Transactional
public class TestDaoImpl extends JpaDaoSupport implements TestDao
{
	@Override
	public void delete(Test entity)
	{
		getJpaTemplate().remove(entity);
	}

	@Override
	public void deleteById(Integer id)
	{
		getJpaTemplate().remove(getJpaTemplate().getReference(Test.class, id));
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Test getById(Integer id)
	{
		return getJpaTemplate().find(Test.class, id);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Test> getAll()
	{
		return getJpaTemplate().find("select test from Test test");
	}

	@Override
	public void save(Test entity)
	{
		getJpaTemplate().persist(entity);
	}

	@Override
	public void update(Test entity)
	{
		getJpaTemplate().merge(entity);
	}
}
