using System;
using System.Collections;
using System.Linq;
using System.Web;
using Spring.Data.NHibernate;
using NHibernate;
using ash2.Dao.Interf;
using model;
using Spring.Data.NHibernate.Support;
using Spring.Transaction;
using Spring.Stereotype;
using Spring.Transaction.Interceptor;

namespace ash2.Dao.Hibernate
{
    //[Repository]
    public class Tb_userDao : HibernateDaoSupport, ITb_userDao
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

        public void delete(Tb_user model)
        {
            //sf.GetCurrentSession().Delete(tb_user);
            HibernateTemplate.Delete(model);
        }

        public Tb_user get(int id)
        {
            //setHibernateTemplate();
            return HibernateTemplate.Get(typeof(Tb_user), id) as Tb_user;
        }

        //[Transaction(Spring.Transaction.TransactionPropagation.Required, ReadOnly = false)]
        //[Transaction]
        public void save(Tb_user model)
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
            HibernateTemplate.Save(model);

            //ISession s = HibernateTemplate.SessionFactory.OpenSession();
            //ITransaction t = s.BeginTransaction();
            //s.Save(model);
            //t.Commit();
            //s.Close();
        }

        public void update(Tb_user model)
        {
            //setHibernateTemplate();
            //HibernateTemplate.Update(person);
            HibernateTemplate.Update(model);
        }

        public IList getAll()
        {
            //setHibernateTemplate();
            //return HibernateTemplate.Find("from Tb_user");
            return HibernateTemplate.Find("from Tb_user");
        }
    }
}
