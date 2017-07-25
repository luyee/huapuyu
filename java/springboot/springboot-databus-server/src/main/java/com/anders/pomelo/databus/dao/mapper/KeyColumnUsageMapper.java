package com.anders.pomelo.databus.dao.mapper;

import java.util.List;

import com.anders.pomelo.databus.dao.bo.KeyColumnUsage;

public interface KeyColumnUsageMapper {

	List<KeyColumnUsage> selectByTableSchema(String tableSchema);
}