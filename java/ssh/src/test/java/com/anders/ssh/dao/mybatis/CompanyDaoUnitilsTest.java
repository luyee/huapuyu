package com.anders.ssh.dao.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.anders.ssh.bo.test.Company;
import com.anders.ssh.bo.test.CompanyInfo;

@RunWith(UnitilsJUnit4TestClassRunner.class)
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

	@Test
	@DataSet("CompanyDaoUnitilsTest-testGetAll.xml")
	@Transactional(transactionManagerName = "mybatisTxManager")
	public void testGetCompanyById() {
		Company company = companyDao.getById(1L);
		Assert.assertEquals("anders", company.getName());
		System.out.println(company.getCompanyInfo());
		System.out.println(company.getAccounts());
		System.out.println(company.getDepartments());
	}

	@Test
	@DataSet("CompanyDaoUnitilsTest-testGetAll.xml")
	@Transactional(transactionManagerName = "mybatisTxManager")
	public void testBatchUpdate() {
		List<Company> list = new ArrayList<Company>();
		Company company = new Company();
		company.setId(1L);
		company.setName("zhuzhen");
		list.add(company);

		company = new Company();
		company.setId(2L);
		company.setName("guolili");
		list.add(company);

		companyDao.batchUpdate(list);
	}

	@Test
	@DataSet("CompanyDaoUnitilsTest-testGetAll.xml")
	@Transactional(transactionManagerName = "mybatisTxManager")
	public void testBatchSelect() {
		List<Long> list = new ArrayList<Long>();
		list.add(1L);
		list.add(2L);

		List<Company> companies = companyDao.batchSelect(list);
		Assert.assertEquals(2L, companies.size());
	}

	@Test
	@DataSet("CompanyDaoUnitilsTest-testGetAll.xml")
	@Transactional(transactionManagerName = "mybatisTxManager")
	public void testGetCompanyInfoById() {
		List<CompanyInfo> list = companyDao.getCompanyInfoById(1L);
		Assert.assertEquals(2, list.size());
	}
}
