using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using fmall.Models;
using Spring.Data.NHibernate.Generic.Support;

namespace fmall.Dao
{
    public class UserDao : HibernateDaoSupport, IDao<User, long>
    {
        public void save(User user)
        {
            HibernateTemplate.Save(user);
        }
    }
}