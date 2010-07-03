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
            Tb_user t1 = new Tb_user();
            t1.id = 123;
            t1.name = "zhuzhen";
            t1.pwd = "321";
            dao.save(t1);

            Tb_user t2 = new Tb_user();
            t2.id = 321;
            t2.name = "guolili";
            t2.pwd = "123";
            dao.save(t2);

            foreach (Tb_user model in dao.getAll())
            {
                string name = model.name;
            }
        }

        [Transaction(ReadOnly = true)]
        public Tb_user get(int id)
        {
            return dao.get(id);
        }

        [Transaction]
        public void save(Tb_user model)
        {
            dao.save(model);
        }

        [Transaction]
        public void update(Tb_user model)
        {
            dao.update(model);
        }

        [Transaction]
        public void delete(Tb_user model)
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
