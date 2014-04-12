package com.anders.ssh.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.dao.GenericDao;

public interface CompanyDao extends GenericDao<Long, Company> {
	List<String> getAccountNameByName(final String name);

	HibernateTemplate getHibernateTemplate();
}
