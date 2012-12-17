package com.anders.ssh.dao.hibernate;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.bo.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-test.xml" })
public class ResourceDaoTest {
	@javax.annotation.Resource(name = "hibernateResourceDao")
	private ResourceDao resourceDao;

	@Test
	public void testCRUD() {
		// 增
		Resource resource = new Resource();
		resource.setName("登录");
		resource.setContent("login.do");
		resourceDao.save(resource);

		Assert.assertNotNull(resource.getId());

		List<Resource> resources = resourceDao.getAll();

		Assert.assertEquals(1, resources.size());
		Assert.assertEquals("登录", resources.get(0).getName());

		// 改
		resource.setName("退出");
		resource.setContent("logout.do");
		resourceDao.update(resource);

		resources = resourceDao.getAll();

		Assert.assertEquals(1, resources.size());
		Assert.assertEquals("退出", resources.get(0).getName());

		// 查
		resource = resourceDao.getById(resource.getId());

		Assert.assertEquals("退出", resource.getName());
	}
}
