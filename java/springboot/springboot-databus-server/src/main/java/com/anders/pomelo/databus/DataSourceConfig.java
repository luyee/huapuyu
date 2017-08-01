package com.anders.pomelo.databus;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

//@Configuration
@Deprecated
public class DataSourceConfig {

	// @Bean(name = "dataSource")
	// @Qualifier("dataSource")
	// @Primary
	// @ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	// @Bean(name = "slaveDataSource")
	// @Qualifier("slaveDataSource")
	// @ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource secondaryDataSource() {
		return DataSourceBuilder.create().build();
	}

}