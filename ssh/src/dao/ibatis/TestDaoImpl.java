package dao.ibatis;

import java.util.List;

import javax.annotation.Resource;

import model.Test;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;

import dao.interf.TestDao;

@Component
public class TestDaoImpl extends SqlMapClientDaoSupport implements TestDao
{
	// 增加setSqlMapClientMocker方法，避免在XML文件中给DAO方法注入SqlMapClient。
	@Resource
	public void setSqlMapClientMocker(SqlMapClient sqlMapClient)
	{
		super.setSqlMapClient(sqlMapClient);
	}

	@Override
	public void delete(Test entity)
	{
		getSqlMapClientTemplate().delete("userDelete", entity);
	}

	@Override
	public Test getById(Integer id)
	{
		return (Test) getSqlMapClientTemplate().queryForObject("userGetById", id);
	}

	@Override
	public void save(Test entity)
	{
		getSqlMapClientTemplate().insert("userInsert", entity);

		Test test = new Test();
		test.setName("012345678901234567890123456789");
		test.setPwd("123");
		getSqlMapClientTemplate().insert("userInsert", test);
	}

	@Override
	public void update(Test entity)
	{
		getSqlMapClientTemplate().update("userUpdate", entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAll()
	{
		return getSqlMapClientTemplate().queryForList("userGetAll");
	}

	@Override
	public void deleteById(Integer id)
	{
		getSqlMapClientTemplate().delete("userDeleteById", id);
	}

}
