package dao.jdbc;

import java.util.List;

import model.Tb_user;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.interf.ITb_userDao;

@Transactional
public class Tb_userDao extends JdbcDaoSupport implements ITb_userDao
{
	/**
	 * 碰到check（Exception）异常，不会回滚；碰到uncheck（RuntimeException）异常，就会回滚
	 */
	// @Transactional(rollbackFor = Exception.class)
	@Transactional(noRollbackFor = RuntimeException.class)
	public void delete(Tb_user model)
	{
		Object[] object = new Object[] { model.getId() };
		getJdbcTemplate().update("DELETE FROM tb_user WHERE id = ?", object);
	}

	public void deleteById(int id)
	{
		Object[] object = new Object[] { id };
		getJdbcTemplate().update("DELETE FROM tb_user WHERE id = ?", object);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Tb_user get(int id)
	{
		Object[] object = new Object[] { id };
		return (Tb_user) getJdbcTemplate().queryForObject("SELECT * FROM tb_user WHERE id = ?", object, new BeanPropertyRowMapper(Tb_user.class));
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List getAll()
	{
		return getJdbcTemplate().query("SELECT * FROM tb_user", new BeanPropertyRowMapper(Tb_user.class));
	}

	public void save(Tb_user model)
	{
		Object[] object = new Object[] { model.getId(), model.getName(), model.getPwd() };
		getJdbcTemplate().update("INSERT INTO tb_user (id, name, pwd) VALUES (?, ?, ?)", object);
	}

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void update(Tb_user model)
	{
		Object[] object = new Object[] { model.getName(), model.getPwd(), model.getId() };
		getJdbcTemplate().update("UPDATE tb_user SET name = ?, pwd = ? WHERE id = ?", object);
	}
}
