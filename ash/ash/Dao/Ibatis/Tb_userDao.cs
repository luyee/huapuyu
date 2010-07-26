using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using ash.Dao.Interf;
using model;
using System.Collections;
using IBatisNet.DataMapper;

namespace ash.Dao.Ibatis
{
    public class Tb_userDao : ITb_userDao
    {
        private ISqlMapper sqlMap;

        public Tb_user get(int id)
        {
            throw new NotImplementedException();
        }

        public void save(Tb_user model)
        {
            this.sqlMap.Insert("Tb_userInsert", model);
        }

        public void update(Tb_user model)
        {
        }

        public void delete(Tb_user model)
        {
            throw new NotImplementedException();
        }

        public IList getAll()
        {
            return null;
        }
    }
}