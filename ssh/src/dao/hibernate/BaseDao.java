package dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.interf.IDao;

public abstract class BaseDao<PK extends Serializable, T> extends HibernateDaoSupport implements IDao<PK, T>
{
	// 增加setSessionFactoryMocker方法，避免在XML文件中给DAO方法注入SessionFactory。
	@Resource
	public void setSessionFactoryMocker(SessionFactory sessionFactory)
	{
		super.setSessionFactory(sessionFactory);
	}

	private Class<T> entityClass;

	public BaseDao()
	{
		entityClass = getSuperClassGenricType();
	}

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

	public void deleteById(PK id)
	{
		getHibernateTemplate().delete(this.getById(id));
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

	public List<T> getAll()
	{
		return getHibernateTemplate().loadAll(entityClass);
	}
}
