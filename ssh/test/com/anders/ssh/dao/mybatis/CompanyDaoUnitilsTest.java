package com.anders.ssh.dao.mybatis;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.database.annotations.Transactional;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.anders.ssh.bo.test.Company;

@SpringApplicationContext("classpath:spring-test.xml")
public class CompanyDaoUnitilsTest extends UnitilsJUnit4 {

	@SpringBean("mybatisCompanyDao")
	private CompanyDao companyDao;

	@Test
	@DataSet("CompanyDaoUnitilsTest-testGetAll.xml")
	@Transactional(transactionManagerName = "mybatisTxManager")
	public void testGetAll() {
		List<Company> companyList = companyDao.getAll();
		for (Company company : companyList) {
			Assert.assertEquals(1, company.getId().intValue());
			Assert.assertEquals("anders", company.getName());
			Assert.assertEquals(2, company.getAccounts().size());
			Assert.assertEquals("zhuzhen", company.getAccounts().get(0).getName());
			Assert.assertEquals("guolili", company.getAccounts().get(1).getName());
			Assert.assertEquals(2, company.getDepartments().size());
			Assert.assertEquals("销售", company.getDepartments().get(0).getName());
			Assert.assertEquals("研发", company.getDepartments().get(1).getName());
			Assert.assertEquals("朱振的公司", company.getCompanyInfo().getInfo());
		}
	}
}
