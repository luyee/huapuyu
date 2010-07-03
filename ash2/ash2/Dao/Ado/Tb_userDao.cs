using System;
using System.Collections;
using System.Linq;
using System.Web;
using ash2.Dao.Interf;
using Spring.Data.Core;
using model;
using System.Data;
using Spring.Data.Common;

namespace ash2.Dao.Ado
{
    public class Tb_userDao : AdoDaoSupport, ITb_userDao
    {
        public Tb_user get(int id)
        {
            IDbParametersBuilder builder = CreateDbParametersBuilder();
            builder.Create().Name("id").Type(DbType.Int32).Value(id);

            return AdoTemplate.QueryForObject(CommandType.Text, @"SELECT * FROM tb_user WHERE id = :id", new Tb_userRowMapper(), builder.GetParameters()) as Tb_user;
        }

        public void save(Tb_user model)
        {
            IDbParameters parameters = CreateDbParameters();
            parameters.AddWithValue("id", model.id).DbType = DbType.Int32;
            parameters.AddWithValue("name", model.name).DbType = DbType.String;
            parameters.AddWithValue("pwd", model.pwd).DbType = DbType.String;

            //sql server
            //AdoTemplate.ExecuteNonQuery(CommandType.Text, @"INSERT INTO tb_user (id, name, pwd) VALUES (@id, @name, @pwd)", parameters);
            //oracle
            AdoTemplate.ExecuteNonQuery(CommandType.Text, @"INSERT INTO tb_user (id, name, pwd) VALUES (:id, :name, :pwd)", parameters);
        }

        public void update(Tb_user model)
        {
            IDbParameters parameters = CreateDbParameters();
            parameters.AddWithValue("id", model.id).DbType = DbType.Int32;
            parameters.AddWithValue("name", model.name).DbType = DbType.String;
            parameters.AddWithValue("pwd", model.pwd).DbType = DbType.String;

            AdoTemplate.ExecuteNonQuery(CommandType.Text, @"UPDATE tb_user SET name = :name, pwd = :pwd WHERE id = :id", parameters);
        }

        public void delete(Tb_user model)
        {
            IDbParametersBuilder builder = CreateDbParametersBuilder();
            builder.Create().Name("id").Type(DbType.Int32).Value(model.id);

            AdoTemplate.ExecuteNonQuery(CommandType.Text, @"DELETE FROM tb_user WHERE id = :id", builder.GetParameters());
        }

        public IList getAll()
        {
            return AdoTemplate.QueryWithRowMapper(CommandType.Text, @"SELECT * FROM tb_user", new Tb_userRowMapper());
        }
    }
}
