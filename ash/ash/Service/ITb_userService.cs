using System;
using System.Collections;
using model;

namespace ash.Service
{
    public interface ITb_userService
    {
        void test();

        User get(int id);

        void save(User model);

        void update(User model);

        void delete(User model);

        IList getAll();
    }
}
