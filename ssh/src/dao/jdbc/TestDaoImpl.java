package dao.jdbc;

import java.util.List;

import model.Test;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.interf.TestDao;

@Transactional
public class TestDaoImpl extends JdbcDaoSupport implements TestDao
{
	/**
	 * 碰到check（Exception）异常，不会回滚；碰到uncheck（RuntimeException）异常，就会回滚
	 */
	// @Transactional(rollbackFor = Exception.class)
	@Transactional(noRollbackFor = RuntimeException.class)
	public void delete(Test entity)
	{
		Object[] object = new Object[] { entity.getId() };
		getJdbcTemplate().update("DELETE FROM tb_test WHERE id = ?", object);
	}

	public void deleteById(Integer id)
	{
		Object[] object = new Object[] { id };
		getJdbcTemplate().update("DELETE FROM tb_test WHERE id = ?", object);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Test getById(Integer id)
	{
		Object[] object = new Object[] { id };
		return (Test) getJdbcTemplate().queryForObject("SELECT * FROM tb_test WHERE id = ?", object, new BeanPropertyRowMapper(Test.class));
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List getAll()
	{
		return getJdbcTemplate().query("SELECT * FROM tb_test", new BeanPropertyRowMapper(Test.class));
	}

	public void save(Test entity)
	{
		Object[] object = new Object[] { entity.getId(), entity.getName(), entity.getPwd() };
		getJdbcTemplate().update("INSERT INTO tb_test (id, name, pwd) VALUES (?, ?, ?)", object);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void update(Test entity)
	{
		Object[] object = new Object[] { entity.getName(), entity.getPwd(), entity.getId() };
		getJdbcTemplate().update("UPDATE tb_test SET name = ?, pwd = ? WHERE id = ?", object);
	}
}
