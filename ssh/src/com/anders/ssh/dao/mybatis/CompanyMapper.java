package com.anders.ssh.dao.mybatis;

import java.util.List;

import com.anders.ssh.model.test.Company;

public interface CompanyMapper {
	// List<Company> getAllSqlBySql();

	List<Company> getAll();
}
