package com.anders.pomelo.databus.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anders.pomelo.databus.dao.bo.KeyColumnUsage;

@Mapper
public interface KeyColumnUsageMapper {

	List<KeyColumnUsage> selectByTableSchema(String tableSchema);
}