using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace fmall.Dao
{
    public interface IDao<T, PK>
    {
        void save(T entity);
    }
}
