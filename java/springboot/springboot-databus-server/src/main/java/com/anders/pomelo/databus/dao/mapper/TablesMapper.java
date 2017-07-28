package com.anders.pomelo.databus.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anders.pomelo.databus.dao.bo.Tables;

@Mapper
public interface TablesMapper {

	List<Tables> selectByTableSchema(String tableSchema);
}