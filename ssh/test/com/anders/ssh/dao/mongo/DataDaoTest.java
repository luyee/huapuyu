package com.anders.ssh.dao.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.Account;
import com.anders.ssh.bo.test.Company;
import com.anders.ssh.bo.xml.Data;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class DataDaoTest {

	@Resource(name = "mongoDataDao")
	private DataDao dataDao;

	@Resource(name = "mongoCompanyDao")
	private CompanyDao companyDao;

	@Test
	public void test1() {
		Data data = new Data();
		data.setId(1L);
		data.setName("zhuzhen");
		data.setType((byte) 1);
		data.setEnable(true);
		// 增
		dataDao.save(data);
		// 查
		Data dataTemp = dataDao.getById(1);
		System.out.println(dataTemp);
		data.setName("guolili");
		// 改
		dataDao.update(data);
		// 查
		dataTemp = dataDao.getById(1);
		System.out.println(dataTemp);
		// 删
		// dataDao.delete(data);
		// 查
		// List<Data> list = dataDao.getAll();
		// Assert.assertEquals(0, list.size());
	}

	@Test
	public void test2() {
		List<Account> accounts = new ArrayList<Account>();

		Account account = new Account();
		account.setId(111L);
		account.setName("account1");
		accounts.add(account);

		account = new Account();
		account.setId(112L);
		account.setName("account2");
		accounts.add(account);

		Company company = new Company();
		company.setId(222L);
		company.setName("company1");
		company.setAccounts(accounts);

		companyDao.save(company);

		List<Company> companyList = companyDao.getByAccountName("account1");
		System.out.println(companyList.size());
	}

	public void testInsert5000000() {
		// 插入500万需要42分钟
		Long beginTime = new Date().getTime();
		for (int i = 1; i <= 5000000; i++) {
			Data data = new Data();
			data.setId(new Long(i));
			data.setName("zhuzhen");
			data.setType((byte) 1);
			data.setEnable(true);
			dataDao.save(data);
		}
		Long endTime = new Date().getTime();
		System.out.println("耗时：" + (endTime - beginTime) / 1000 / 60 + "分");
	}
}
