package com.anders.ssh.dao.mongo.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.dao.mongo.CompanyDao;
import com.anders.ssh.dao.mongo.MongoDao;

@Component("mongoCompanyDao")
public class CompanyDaoImpl extends MongoDao<Long, Company> implements CompanyDao {
	@Override
	public List<Company> getByAccountName(String name) {
		return mongoTemplate.find(new Query(Criteria.where("accounts.name").is(name)), Company.class);
	}
}
