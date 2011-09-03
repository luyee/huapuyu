package com.anders.ssh.dao.jdbc;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anders.ssh.model.xml.Data;

@Transactional
//@Component("jdbcDataDao")
public class DataDao extends JdbcDao<Integer, Data>
{

	// TODO Anders Zhu : 碰到check（Exception）异常，不会回滚；碰到uncheck（RuntimeException）异常，就会回滚
	@Override
	// @Transactional(rollbackFor = Exception.class)
	@Transactional(noRollbackFor = RuntimeException.class)
	public void delete(Data entity)
	{
		Object[] object = new Object[] { entity.getId() };
		getJdbcTemplate().update("DELETE FROM tb_data WHERE id = ?", object);
	}

	@Override
	public void deleteById(Integer id)
	{
		Object[] object = new Object[] { id };
		getJdbcTemplate().update("DELETE FROM tb_data WHERE id = ?", object);
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public Data getById(Integer id)
	{
		Object[] object = new Object[] { id };
		return (Data) getJdbcTemplate().queryForObject("SELECT * FROM tb_data WHERE id = ?", object, new BeanPropertyRowMapper(Data.class));
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<Data> getAll()
	{
		return getJdbcTemplate().query("SELECT * FROM tb_data", new BeanPropertyRowMapper(Data.class));
	}

	@Override
	public void save(Data entity)
	{
		Object[] object = new Object[] { entity.getId(), entity.getName(), entity.getType() };
		getJdbcTemplate().update("INSERT INTO tb_data (id, name, type) VALUES (?, ?, ?)", object);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void update(Data entity)
	{
		Object[] object = new Object[] { entity.getName(), entity.getType(), entity.getId() };
		getJdbcTemplate().update("UPDATE tb_data SET name = ?, type = ? WHERE id = ?", object);
	}
}
