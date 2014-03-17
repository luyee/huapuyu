package com.anders.ssh.dao.mybatis.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.dao.mybatis.AccountDao;
import com.anders.ssh.dao.mybatis.AccountMapper;
import com.anders.ssh.dao.mybatis.GenericMapper;
import com.anders.ssh.dao.mybatis.MybatisDao;

@Component("mybatisAccountDao")
public class AccountDaoImpl extends MybatisDao<Long, Account> implements AccountDao {

	@Resource
	private AccountMapper accountMapper;

	@Override
	protected GenericMapper<Long, Account> getMapper() {
		return accountMapper;
	}

	@Override
	public void delete(Account account) {
		Assert.notNull(account);
		throw new RuntimeException("没有实现");
	}

	@Override
	public void deleteById(Long id) {
		Assert.notNull(id);
		accountMapper.deleteById(id);
	}

	@Override
	public List<Account> getAll() {
		throw new RuntimeException("没有实现");
	}

	@Override
	public Account getById(Long id) {
		Assert.notNull(id);
		return accountMapper.getById(id);
	}

	@Override
	public void save(Account account) {
		Assert.notNull(account);
		accountMapper.save(account);
	}

	@Override
	public void saveOrUpdate(Account account) {
		Assert.notNull(account);
		throw new RuntimeException("没有实现");
	}

	@Override
	public void update(Account account) {
		Assert.notNull(account);
		accountMapper.update(account);
	}

}
