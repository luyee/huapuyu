using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using model;
using Spring.Stereotype;
using ash3.Dao;
using Spring.Data.NHibernate.Generic.Support;

namespace ash3.Dao.Hibernate
{
    //[Repository]
    public class UserDao : HibernateDaoSupport, IDao<User, long?>
    {
        //private HibernateTemplate ht;
        //public ISessionFactory sf { set; get; }

        //private void setHibernateTemplate()
        //{
        //    if (null == ht)
        //    {
        //        ht = new HibernateTemplate(this.sf);
        //        ht.TemplateFlushMode = TemplateFlushMode.Auto;
        //        ht.CacheQueries = true;
        //    }
        //}

        public void delete(User user)
        {
            //sf.GetCurrentSession().Delete(tb_user);
            HibernateTemplate.Delete(user);
        }

        public User getById(long? id)
        {
            //setHibernateTemplate();
            return HibernateTemplate.Get<User>(id);
        }

        //[Transaction(Spring.Transaction.TransactionPropagation.Required, ReadOnly = false)]
        //[Transaction]
        public void save(User user)
        {
            //setHibernateTemplate();
            //sf.GetCurrentSession().Save(person);
            //ht.Save(person);
            //ISession s = sf.OpenSession();
            //ITransaction t = s.BeginTransaction();
            //s.FlushMode = FlushMode.Auto;
            //s.Save(person);
            //t.Commit();
            //HibernateTemplate.TemplateFlushMode = TemplateFlushMode.Auto;
            HibernateTemplate.Save(user);

            //ISession s = HibernateTemplate.SessionFactory.OpenSession();
            //ITransaction t = s.BeginTransaction();
            //s.Save(model);
            //t.Commit();
            //s.Close();
        }

        public void update(User user)
        {
            //setHibernateTemplate();
            //HibernateTemplate.Update(person);
            HibernateTemplate.Update(user);
        }

        public IList<User> getAll()
        {
            //setHibernateTemplate();
            //return HibernateTemplate.Find("from Tb_user");
            return HibernateTemplate.Find<User>("from User");
        }
    }
}
