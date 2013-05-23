package com.anders.ssh.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.anders.ssh.bo.test.Company;

@Component("mongoCompanyDao")
public class CompanyDao extends MongoDao<Integer, Company> {

	@Override
	public void delete(Company account) {
		mongoTemplate.remove(account);
	}

	@Override
	public void deleteById(Integer id) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Company.class);
	}

	@Override
	public List<Company> getAll() {
		return mongoTemplate.findAll(Company.class);
	}

	@Override
	public Company getById(Integer id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Company.class);
	}

	@Override
	public void save(Company company) {
		// mongoTemplate.save(account);
		mongoTemplate.insert(company);
	}

	@Override
	public void update(Company company) {
		mongoTemplate.updateFirst(new Query(Criteria.where("id").is(company.getId())), Update.update("name", company.getName()), Company.class);
	}

	@Override
	public void saveOrUpdate(Company company) {
	}

	public List<Company> getByAccountName(String name) {
		return mongoTemplate.find(new Query(Criteria.where("accounts.name").is(name)), Company.class);
	}
}
