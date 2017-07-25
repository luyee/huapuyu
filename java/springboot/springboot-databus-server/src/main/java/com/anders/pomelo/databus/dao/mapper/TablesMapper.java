package com.anders.pomelo.databus.dao.mapper;

import java.util.List;

import com.anders.pomelo.databus.dao.bo.Tables;

public interface TablesMapper {
	
	List<Tables> selectByTableSchema(String tableSchema);
}