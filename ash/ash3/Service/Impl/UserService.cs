using System;
using System.Linq;
using System.Web;
using model;
using Spring.Transaction.Interceptor;
using System.Collections.Generic;
using ash3.Service;
using ash3.Dao.Hibernate;

namespace ash3.Service.Impl
{
    [Transaction]
    public class UserService : IUserService
    {
        public UserDao userDao { set; get; }

        [Transaction(ReadOnly = true)]
        public User getById(long? id)
        {
            return userDao.getById(id);
        }

        [Transaction]
        public void save(User model)
        {
            userDao.save(model);
        }

        [Transaction]
        public void update(User model)
        {
            userDao.update(model);
        }

        [Transaction]
        public void delete(User model)
        {
            userDao.delete(model);
        }

        [Transaction(ReadOnly = true)]
        public IList<User> getAll()
        {
            return userDao.getAll();
        }
    }
}
