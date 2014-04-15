package com.anders.ssh.dao.mybatis;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.mybatis.MyBatisMapper;

@MyBatisMapper
@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface AccountMapper extends GenericMapper<Long, Account> {
	@Insert("INSERT INTO tb_account (id, name, enable) VALUES (#{id}, #{name}, #{enable})")
	@Options(flushCache = true)
	void save(Account account);

	@Delete("DELETE FROM tb_account WHERE id = #{id}")
	@Options(flushCache = true)
	void deleteById(Long id);

	@Select("SELECT * FROM tb_account WHERE id = #{id}")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name"), @Result(property = "enable", column = "enable") })
	Account getById(Long id);

	@Update("UPDATE tb_account SET name = #{name}, enable = #{enable} WHERE id = #{id}")
	@Options(flushCache = true)
	void update(Account account);
}
