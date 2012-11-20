package com.anders.ssh.dao.mybatis;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.model.test.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class CompanyDaoTest {

	@Resource(name = "mybatisCompanyDao")
	private CompanyDao companyDao;

	@Test
	public void test1() {
		List<Company> companyList = companyDao.getAll();
		for (Company company : companyList) {
			Assert.assertEquals(1, company.getId().intValue());
			Assert.assertEquals("anders", company.getName());
			Assert.assertEquals(2, company.getAccounts().size());
			Assert.assertEquals("guolili", company.getAccounts().get(0).getName());
			Assert.assertEquals("zhuzhen", company.getAccounts().get(1).getName());
			Assert.assertEquals(2, company.getDepartments().size());
			Assert.assertEquals("soft", company.getDepartments().get(0).getName());
			Assert.assertEquals("sell", company.getDepartments().get(1).getName());
			Assert.assertEquals("maizu", company.getCompanyInfo().getInfo());
		}
	}
}
