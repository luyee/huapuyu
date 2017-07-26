package com.anders.pomelo.databus.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DatabaseMapper {

	String selectCharacterSetServer();
}