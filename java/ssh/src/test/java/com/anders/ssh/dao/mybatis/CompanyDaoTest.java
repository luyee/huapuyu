package com.anders.ssh.dao.mybatis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.UnitilsJUnit4;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.bo.test.CompanyInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis-test.xml" })
public class CompanyDaoTest extends UnitilsJUnit4 {

	@Resource
	private CompanyDao companyDao;

	@Test
	@Transactional(transactionManagerName = "mybatisTxManager")
	public void testInsert() {
		Company company = new Company();
		company.setName("zhuzhen");
		companyDao.save(company);
		System.out.println(company.getId());
	}
}
