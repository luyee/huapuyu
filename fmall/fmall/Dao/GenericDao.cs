using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Spring.Data.NHibernate.Generic.Support;

namespace fmall.Dao
{
    public class GenericDao<T, PK> : HibernateDaoSupport, IGenericDao<T, PK>
    {


        public void save(T entity)
        {
            HibernateTemplate.Save(entity);
        }

        public void update(T entity)
        {
            HibernateTemplate.Update(entity);
        }

        public void delete(T entity)
        {
            HibernateTemplate.Delete(entity);
        }

        public void saveOrUpdate(T entity)
        {
            HibernateTemplate.SaveOrUpdate(entity);
        }

        public T getById(PK id)
        {
            return HibernateTemplate.Get<T>(id);
        }

        public void deleteById(PK id)
        {
          T entity = getById(id);
           HibernateTemplate.Delete(entity);
        }

        public void disabledById(PK id)
        {
            //T entity = getById(id);
            //entity
        }

        public IList<T> getByIds(ICollection<PK> ids)
        {
            throw new NotImplementedException();
        }

        public IList<T> findBy(string propertyName, object value)
        {
            throw new NotImplementedException();
        }

        public System.Collections.IList findBy(string propertyName, object value, string selectPropertyName)
        {
            throw new NotImplementedException();
        }

        public T findUniqueBy(string propertyName, object value)
        {
            throw new NotImplementedException();
        }
    }
}
