package com.anders.ssh.dao.mybatis.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.bo.test.CompanyInfo;
import com.anders.ssh.dao.mybatis.CompanyDao;
import com.anders.ssh.dao.mybatis.CompanyMapper;
import com.anders.ssh.dao.mybatis.GenericMapper;
import com.anders.ssh.dao.mybatis.MybatisDao;

@Component("mybatisCompanyDao")
public class CompanyDaoImpl extends MybatisDao<Long, Company> implements CompanyDao {

	@Resource
	private CompanyMapper companyMapper;

	@Override
	protected GenericMapper<Long, Company> getMapper() {
		return companyMapper;
	}

	@Override
	public void delete(Company company) {
		Assert.notNull(company);
		throw new RuntimeException("没有实现");
	}

	@Override
	public void deleteById(Long id) {
		Assert.notNull(id);
		throw new RuntimeException("没有实现");
	}

	@Override
	public List<Company> getAll() {
		// return companyMapper.getAllSqlBySql();
		return companyMapper.getAll();
	}

	@Override
	public Company getById(Long id) {
		Assert.notNull(id);
		return companyMapper.getById(id);
	}

	@Override
	public void save(Company company) {
		companyMapper.insert(company);
	}

	@Override
	public void update(Company company) {
		Assert.notNull(company);
		throw new RuntimeException("没有实现");
	}

	@Override
	public void saveOrUpdate(Company company) {
		Assert.notNull(company);
		throw new RuntimeException("没有实现");
	}

	@Override
	public void batchUpdate(List<Company> list) {
		companyMapper.batchUpdate(list);
	}

	@Override
	public List<Company> batchSelect(List<Long> list) {
		return companyMapper.batchSelect(list);
	}

	@Override
	public List<CompanyInfo> getCompanyInfoById(Long id) {
		return companyMapper.getCompanyInfoById(id);
	}

}
