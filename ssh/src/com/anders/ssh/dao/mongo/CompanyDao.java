package com.anders.ssh.dao.mongo;

import java.util.List;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.dao.GenericDao;

public interface CompanyDao extends GenericDao<Long, Company> {
	List<Company> getByAccountName(String name);
}
