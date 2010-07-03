using System;
using System.Collections;
using model;

namespace ash.Service
{
    public interface ITb_userService
    {
        void test();

        Tb_user get(int id);

        void save(Tb_user model);

        void update(Tb_user model);

        void delete(Tb_user model);

        IList getAll();
    }
}
