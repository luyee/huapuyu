package com.anders.ssh.dao.mybatis;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.mybatis.MyBatisMapper;

@MyBatisMapper
public interface CompanyMapper extends GenericMapper<Long, Company> {
}
