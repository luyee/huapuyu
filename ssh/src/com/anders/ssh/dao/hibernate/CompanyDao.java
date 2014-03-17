package com.anders.ssh.dao.hibernate;

import java.util.List;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.dao.GenericDao;

public interface CompanyDao extends GenericDao<Long, Company> {
	List<String> getAccountNameByName(final String name);
}
