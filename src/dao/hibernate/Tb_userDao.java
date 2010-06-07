package dao.hibernate;

import java.util.List;

import model.Tb_user;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import dao.interf.ITb_userDao;

public class Tb_userDao extends HibernateDaoSupport implements ITb_userDao
{
	// private HibernateTemplate ht;
	// private SessionFactory sessionFactory;
	//
	// private void setHibernateTemplate()
	// {
	// if (null == ht)
	// {
	// ht = new HibernateTemplate(this.sessionFactory);
	// }
	// }
	//
	// public void setSessionFactory(SessionFactory sessionFactory)
	// {
	// this.sessionFactory = sessionFactory;get
	// }

	public void delete(Tb_user model)
	{
		// setHibernateTemplate();
		// ht.delete(model);
		getHibernateTemplate().delete(model);
	}

	public void deleteById(int id)
	{
		// setHibernateTemplate();
		// ht.delete(model);
		Tb_user model = new Tb_user();
		model.setId(id);
		delete(model);
	}

	public Tb_user get(int id)
	{
		// Session session =
		// getHibernateTemplate().getSessionFactory().openSession();
		// session.get(Tb_user.class, new Integer(id));
		return (Tb_user) getHibernateTemplate().get(Tb_user.class, new Integer(id));
	}

	public void save(Tb_user model)
	{
		getHibernateTemplate().save(model);
	}

	public void update(Tb_user model)
	{
		getHibernateTemplate().update(model);
	}

	@SuppressWarnings("unchecked")
	public List getAll()
	{
		return getHibernateTemplate().find("from Tb_user");
	}
}
