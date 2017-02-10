package com.anders.ssh.dao.jdbc;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.anders.ssh.dao.GenericDao;

public abstract class JdbcDao<PK extends Serializable, T> extends JdbcDaoSupport implements GenericDao<PK, T> {
	// 增加setDataSourceMocker方法，避免在XML文件中给DAO方法注入DataSource。
	@Resource
	public void setSuperDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
}
