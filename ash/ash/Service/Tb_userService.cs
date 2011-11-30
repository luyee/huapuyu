using System;
using System.Linq;
using System.Web;
using model;
using Spring.Transaction.Interceptor;
using ash.Dao.Interf;
using System.Collections;

namespace ash.Service
{
    [Transaction]
    public class Tb_userService : ITb_userService
    {
        public ITb_userDao dao { set; get; }

        [Transaction]
        public void test()
        {
            User t1 = new User();
            t1.id = 123;
            t1.name = "zhuzhen";
            t1.pwd = "321";
            dao.save(t1);

            User t2 = new User();
            t2.id = 321;
            t2.name = "guolili";
            t2.pwd = "123";
            dao.save(t2);

            foreach (User model in dao.getAll())
            {
                string name = model.name;
            }
        }

        [Transaction(ReadOnly = true)]
        public User get(int id)
        {
            return dao.get(id);
        }

        [Transaction]
        public void save(User model)
        {
            dao.save(model);
        }

        [Transaction]
        public void update(User model)
        {
            dao.update(model);
        }

        [Transaction]
        public void delete(User model)
        {
            dao.delete(model);
        }

        [Transaction(ReadOnly = true)]
        public IList getAll()
        {
            return dao.getAll();
        }
    }
}
