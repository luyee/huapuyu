package com.anders.ssh.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.dao.GenericDao;
import com.anders.ssh.dao.hibernate.CompanyDao;
import com.anders.ssh.service.CompanyService;

@Service("comapnyService")
public class CompanyServiceImpl extends GenericServiceImpl<Long, Company> implements CompanyService {

	@Resource(name = "hibernateCompanyDao")
	private CompanyDao companyDao;

	@Override
	protected GenericDao<Long, Company> getDao() {
		return companyDao;
	}
}
