package com.anders.ssh.dao.mybatis;

import java.util.List;

import com.anders.ssh.model.annotation.Company;

public interface CompanyMapper {
	// List<Company> getAllSqlBySql();

	List<Company> getAll();
}
