package dao.ibatis;

import java.util.List;

import model.test.Tb_user;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import dao.interf.ITb_userDao;

public class Tb_userDao extends SqlMapClientDaoSupport implements ITb_userDao
{
	@Override
	public void delete(Tb_user model)
	{
		getSqlMapClientTemplate().delete("tb_userDelete", model);
		// try
		// {
		// getSqlMapClient().delete("Tb_userDelete", tb_user);
		// }
		// catch (SQLException e)
		// {
		// e.printStackTrace();
		// }
	}

	@Override
	public Tb_user get(int id)
	{
		return (Tb_user) getSqlMapClientTemplate().queryForObject("tb_userSelectById", id);
	}

	@Override
	public void save(Tb_user model)
	{
		getSqlMapClientTemplate().insert("tb_userInsert", model);
	}

	@Override
	public void update(Tb_user model)
	{
		getSqlMapClientTemplate().update("tb_userUpdate", model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAll()
	{
		return getSqlMapClientTemplate().queryForList("tb_usersGetAll");
	}

	@Override
	public void deleteById(int id)
	{
		getSqlMapClientTemplate().delete("tb_userDeleteById", id);
	}

}
