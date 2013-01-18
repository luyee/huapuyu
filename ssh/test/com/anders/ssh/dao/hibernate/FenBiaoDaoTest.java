package com.anders.ssh.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.test.FenBiao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class FenBiaoDaoTest {
	@Resource(name = "hibernateFenBiaoDao")
	private FenBiaoDao fenBiaoDao;

	@Test
	public void test1() {
		List<FenBiao> list = fenBiaoDao.getAllData();
		for (FenBiao fenBiao : list) {
			System.out.println(fenBiao.getName());
		}
		System.out.println("hello");
	}
}
