using System;
using System.Linq;
using System.Text;
using model;
using System.Collections.Generic;

namespace ash3.Dao
{
    public interface IDao<TEntity, TId>
    {
        TEntity getById(TId id);

        void save(TEntity entity);

        void update(TEntity entity);

        void delete(TEntity entity);

        IList<TEntity> getAll();
    }
}
