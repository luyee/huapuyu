package com.anders.ssh.mybatis.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.anders.ssh.mybatis.bo.Account;
import com.anders.ssh.mybatis.bo.AccountCriteria;
import com.anders.ssh.mybatis.bo.AccountCriteria.Criteria;
import com.anders.ssh.mybatis.dao.AccountDao;
import com.anders.ssh.mybatis.dao.AccountMapper;

@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

	@Resource(name = "accountMapper")
	private AccountMapper accountMapper;

	@Override
	public void save(Account account) {
		accountMapper.insert(account);
	}

	@Override
	public void deleteById(Long id) {
		accountMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Account getById(Long id) {
		// return accountMapper.selectByPrimaryKey(id);
		AccountCriteria accountCriteria = new AccountCriteria();
		accountCriteria.addId().addName();
		accountCriteria.setDistinct(true);
		Criteria criteria = accountCriteria.createCriteria();
		criteria.andIdEqualTo(1L);
		accountCriteria.setOrderByClause("id");
		accountCriteria.setLimitStart(0);
		accountCriteria.setLimitCount(1);
		// Set<String> fields = new HashSet<String>();
		// fields.add("name");
		return accountMapper.selectByCriteria(accountCriteria).get(0);
	}

	@Override
	public int update(Account entity) {
		return accountMapper.updateByPrimaryKey(entity);
	}

	@Override
	public void delete(Account entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Account> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdate(Account entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateBySelective(Account entity) {
		AccountCriteria accountCriteria = new AccountCriteria();
		accountCriteria.addEnable().addId().addName();
		Criteria criteria = accountCriteria.createCriteria();
		criteria.andNameEqualTo("zhuzhen");

		return accountMapper.updateByCriteriaSelective(entity, accountCriteria);
	}

}
