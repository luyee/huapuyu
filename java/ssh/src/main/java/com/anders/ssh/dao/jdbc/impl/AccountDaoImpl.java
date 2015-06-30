package com.anders.ssh.dao.jdbc.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.jdbc.AccountDao;
import com.anders.ssh.dao.jdbc.JdbcDao;

@Component("jdbcAccountDao")
public class AccountDaoImpl extends JdbcDao<Long, Account> implements AccountDao {

	@Override
	public void delete(Account account) {
		Object[] object = new Object[] { account.getId() };
		getJdbcTemplate().update("DELETE FROM tb_account WHERE id = ?", object);
	}

	@Override
	public void deleteById(Long id) {
		Object[] object = new Object[] { id };
		getJdbcTemplate().update("DELETE FROM tb_account WHERE id = ?", object);
	}

	@Override
	public Account getById(Long id) {
		Object[] object = new Object[] { id };
		return (Account) getJdbcTemplate().queryForObject("SELECT * FROM tb_account WHERE id = ?", object, new BeanPropertyRowMapper(Account.class));
	}

	@Override
	public List<Account> getAll() {
		return getJdbcTemplate().query("SELECT * FROM tb_account", new BeanPropertyRowMapper(Account.class));
	}

	@Override
	public void save(Account account) {
		Object[] object = new Object[] { account.getId(), account.getName(), account.getEnable() };
		getJdbcTemplate().update("INSERT INTO tb_account (id, name, enable) VALUES (?, ?, ?)", object);
	}

	@Override
	public void update(Account account) {
		Object[] object = new Object[] { account.getName(), account.getId() };
		getJdbcTemplate().update("UPDATE tb_account SET name = ? WHERE id = ?", object);
	}

	@Override
	public void saveOrUpdate(Account account) {
		Assert.notNull(account);
		throw new RuntimeException("没有实现");
	}
}
