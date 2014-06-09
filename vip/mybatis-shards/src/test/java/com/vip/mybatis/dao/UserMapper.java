package com.vip.mybatis.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.vip.mybatis.annotation.Mapper;
import com.vip.mybatis.bo.User;

@Mapper
public interface UserMapper {
	@Insert("insert into $[table]$ (id, name) values (#{id}, #{name})")
	// @Shard(name = "shard_user", field = "id")
	void save(User User);

	@Delete("DELETE FROM $[table]$ WHERE id = #{id}")
	// @Shard(name = "shard_user", clazz = User.class, field = "id")
	void deleteById(Long id);

	@Select("SELECT * FROM $[table]$ WHERE id = #{id}")
	// @Shard(name = "shard_user", field = "id")
	@Results({ @Result(property = "id", column = "id"), @Result(property = "name", column = "name") })
	User getById(Long id);

	@Update("update $[table]$ set name = #{name} where id = #{id}")
	// @Shard(name = "shard_user", field = "id")
	void update(User user);
}
