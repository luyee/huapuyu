package com.anders.ssh.dao.mybatis;

import java.util.List;

import com.anders.ssh.annotation.MyBatisMapper;
import com.anders.ssh.bo.test.Company;
import com.anders.ssh.bo.test.CompanyInfo;

@MyBatisMapper
public interface CompanyMapper extends GenericMapper<Long, Company> {

	void batchUpdate(List<Company> list);

	void insert(Company company);
	
	List<Company> batchSelect(List<Long> list);

	List<CompanyInfo> getCompanyInfoById(Long id);
}
