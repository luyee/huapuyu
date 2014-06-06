package com.anders.ssh.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anders.ssh.annotation.MyBatisMapper;
import com.anders.ssh.mybatis.bo.Account;
import com.anders.ssh.mybatis.bo.AccountCriteria;

@MyBatisMapper
public interface AccountMapper {
	int countByCriteria(AccountCriteria example);

	int deleteByCriteria(AccountCriteria example);

	int deleteByPrimaryKey(Long id);

	int insert(Account record);

	int insertSelective(Account record);

	List<Account> selectByCriteria(AccountCriteria example);

	Account selectByPrimaryKey(Long id);

	int updateByCriteriaSelective(@Param("record") Account record, @Param("example") AccountCriteria example);

	int updateByCriteria(@Param("record") Account record, @Param("example") AccountCriteria example);

	int updateByPrimaryKeySelective(Account record);

	int updateByPrimaryKey(Account record);
}