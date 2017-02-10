using System;
using System.Collections.Generic;
using model;

namespace ash3.Service
{
    public interface IUserService
    {
        User getById(long? id);

        void save(User user);

        void update(User user);

        void delete(User user);

        IList<User> getAll();
    }
}
