package dao.ibatis;

import java.util.List;

import model.test.Tb_user;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import dao.interf.ITb_userDao;

@Component
public class Tb_userDao extends SqlMapClientDaoSupport implements ITb_userDao
{
	@Override
	public void delete(Tb_user model)
	{
		getSqlMapClientTemplate().delete("userDelete", model);
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
	public Tb_user getById(int id)
	{
		return (Tb_user) getSqlMapClientTemplate().queryForObject("userGetById", id);
	}

	@Override
	public void save(Tb_user model)
	{
		getSqlMapClientTemplate().insert("userInsert", model);

		Tb_user user = new Tb_user();
		user.setName("012345678901234567890123456789");
		user.setPwd("123");
		getSqlMapClientTemplate().insert("userInsert", user);
	}

	@Override
	public void update(Tb_user model)
	{
		getSqlMapClientTemplate().update("userUpdate", model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAll()
	{
		return getSqlMapClientTemplate().queryForList("userGetAll");
	}

	@Override
	public void deleteById(int id)
	{
		getSqlMapClientTemplate().delete("userDeleteById", id);
	}

}
