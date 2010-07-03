using System;
using System.Linq;
using System.Text;
using model;
using System.Collections;

namespace ash2.Dao.Interf
{
    public interface ITb_userDao
    {
        Tb_user get(int id);

        void save(Tb_user model);

        void update(Tb_user model);

        void delete(Tb_user model);

        IList getAll();
    }
}
