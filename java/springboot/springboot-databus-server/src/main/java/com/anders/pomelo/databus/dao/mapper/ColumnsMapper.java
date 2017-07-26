package com.anders.pomelo.databus.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anders.pomelo.databus.dao.bo.Columns;

@Mapper
public interface ColumnsMapper {

	List<Columns> selectByTableSchema(String tableSchema);
}