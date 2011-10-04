package com.anders.ssh.dao.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.anders.ssh.dao.Dao;
import com.anders.ssh.model.annotation.Company;

@Component("mybatisCompanyDao")
public class CompanyDao implements Dao<Long, Company> {

	@Resource
	private CompanyMapper companyMapper;

	@Override
	public void delete(Company entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Company> getAll() {
		// return companyMapper.getAllSqlBySql();
		return companyMapper.getAll();
	}

	@Override
	public Company getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Company entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Company entity) {
		// TODO Auto-generated method stub

	}

}
