package com.anders.ssh.dao.mybatis;

import com.anders.ssh.annotation.MyBatisMapper;
import com.anders.ssh.bo.test.Company;

@MyBatisMapper
public interface CompanyMapper extends GenericMapper<Long, Company> {
}
