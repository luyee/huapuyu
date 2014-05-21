package com.vipshop.mybatis.spring;

import java.util.Map;

import javax.sql.DataSource;

/**
 * 多数据源支持接口
 * 
 * @author Anders
 * 
 */
public interface MultiDataSourceSupport {

	public DataSource getDefaultDataSource();

	public Map<String, DataSource> getShardDataSources();
}
