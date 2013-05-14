package com.anders.ssh.dao.mybatis;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.anders.ssh.bo.xml.Area;

@CacheNamespace(implementation = org.mybatis.caches.ehcache.EhcacheCache.class)
public interface AreaMapper {
	@Insert("INSERT INTO cfg_area (id, name, type, enable) VALUES (#{id}, #{name}, #{type}, #{enable})")
	@Options(flushCache = true)
	public void save(Area area);

	@Delete("DELETE FROM cfg_area WHERE id = #{id}")
	@Options(flushCache = true)
	public void deleteById(Long id);

	@Select("SELECT * FROM cfg_area WHERE id = #{id}")
	@Results( { @Result(property = "id", column = "id"), @Result(property = "name", column = "name"), @Result(property = "type", column = "type"), @Result(property = "enable", column = "enable") })
	public Area getById(Long id);

	@Update("UPDATE cfg_area SET name = #{name}, type = #{type}, enable = #{enable} WHERE id = #{id}")
	@Options(flushCache = true)
	public void update(Area area);
}
