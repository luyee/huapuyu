using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace fmall.Dao
{
    public interface IGenericDao<T, PK>
    {
        void save(T entity);

        void update(T entity);

        void delete(T entity);

        void saveOrUpdate(T entity);

        T getById(PK id);

        void deleteById(PK id);

        void disabledById(PK id);

        IList<T> getByIds(ICollection<PK> ids);

        IList<T> findBy(String propertyName, Object value);

        System.Collections.IList findBy(String propertyName, Object value, String selectPropertyName);

        T findUniqueBy(String propertyName, Object value);
    }
}
