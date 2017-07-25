package com.anders.pomelo.databus.dao.mapper;

import java.util.List;

import com.anders.pomelo.databus.dao.bo.Columns;

public interface ColumnsMapper {

	List<Columns> selectByTableSchema(String tableSchema);
}