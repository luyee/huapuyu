package dao.hibernate;

import java.util.List;

import model.Test;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.interf.TestDao;

public class TestDaoImpl extends HibernateDaoSupport implements TestDao
{
	public void delete(Test entity)
	{
		getHibernateTemplate().delete(entity);
	}

	public void deleteById(Integer id)
	{
		delete(getById(id));
	}

	public Test getById(Integer id)
	{
		return getHibernateTemplate().get(Test.class, new Integer(id));
	}

	public void save(Test entity)
	{
		getHibernateTemplate().save(entity);
	}

	public void update(Test entity)
	{
		getHibernateTemplate().update(entity);
	}

	public List<Test> getAll()
	{
		// return getHibernateTemplate().find("from Tb_user");
		// http://kewb.javaeye.com/blog/128708
		return (List<Test>) getHibernateTemplate().execute(new HibernateCallback<Object>()
		{
			public Object doInHibernate(Session session) throws HibernateException
			{
				// List result =
				// session.createCriteria(Tb_user.class).add(Restrictions.like("name",
				// name+"%").list();
				// return result;
				return null;
			}
		});

	}
}
