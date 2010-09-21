package dao.hibernate;

import java.io.Serializable;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.interf.IDao;

public class Dao<PK extends Serializable, T> extends HibernateDaoSupport implements IDao<PK, T>
{

	private Class<T> entityClass;

	public Dao()
	{
		// TODO
		// entityClass = getClass();
	}

	@Override
	public void delete(T entity)
	{
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void merge(T entity)
	{
		getHibernateTemplate().merge(entity);
	}

	@Override
	public void persist(T entity)
	{
		getHibernateTemplate().persist(entity);
	}

	@Override
	public void save(T entity)
	{
		getHibernateTemplate().save(entity);
	}

	@Override
	public void saveOrUpdate(T entity)
	{
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public T getById(PK id)
	{
		return getHibernateTemplate().get(entityClass, id);
	}
}
