using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Spring.Data;
using model;

namespace ash2.Dao.Ado
{
    public class Tb_userRowMapper : IRowMapper
    {
        public object MapRow(System.Data.IDataReader reader, int rowNum)
        {
            Tb_user model = new Tb_user();
            model.id = reader.GetInt32(0);
            model.name = reader.GetString(1);
            model.pwd = reader.GetString(2);
            return model;
        }
    }
}
