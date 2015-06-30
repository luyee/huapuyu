package com.anders.ssh.dao.mybatis;

import java.util.List;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.bo.test.CompanyInfo;
import com.anders.ssh.dao.GenericDao;

public interface CompanyDao extends GenericDao<Long, Company> {
	void batchUpdate(List<Company> list);

	List<Company> batchSelect(List<Long> list);

	List<CompanyInfo> getCompanyInfoById(Long id);
}
