package com.anders.ssh.dao.redis;

import java.util.List;

import com.anders.ssh.bo.test.Account;

public interface AccountDao {
	public void delete(Account entity);

	public void deleteById(Long id, Account entity);

	public void save(Long id, Account entity);

	public void update(Account entity);

	public Account getById(Long id);

	public List<Account> getAll();

	public void saveOrUpdate(Account entity);
}
