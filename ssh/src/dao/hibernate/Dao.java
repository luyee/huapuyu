package dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.interf.IDao;

public class Dao<PK extends Serializable, T> extends HibernateDaoSupport implements IDao<PK, T>
{
	private Class<T> entityClass;

	public Dao()
	{
		entityClass = getSuperClassGenricType();
	}

	@SuppressWarnings("unchecked")
	public Class<T> getSuperClassGenricType()
	{
		Type type = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) type).getActualTypeArguments();
		return (Class<T>) params[1];
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

	public List<T> loadAll()
	{
		return getHibernateTemplate().loadAll(entityClass);
	}
}
