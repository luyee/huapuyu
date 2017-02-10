using System;
using System.Linq;
using System.Text;
using model;
using System.Collections;

namespace ash.Dao.Interf
{
    public interface ITb_userDao
    {
        User get(int id);

        void save(User model);

        void update(User model);

        void delete(User model);

        IList getAll();
    }
}
