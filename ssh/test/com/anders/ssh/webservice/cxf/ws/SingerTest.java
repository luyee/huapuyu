package com.anders.ssh.webservice.cxf.ws;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-remote-test.xml" })
public class SingerTest {
	@Autowired
	private SingerService singerService;

	@Test
	public void testGetAllSinger() {
		List<String> list = new ArrayList<String>();
		list.add("tomcat");
		list.add("jboss");
		List<Singer> singerList = singerService.getAllSinger(list);
		Assert.assertEquals(2, singerList.size());
		Assert.assertEquals("zhuzhen", singerList.get(0).getName());
		Assert.assertEquals("guolili", singerList.get(1).getName());
	}

	@Test
	public void getSinger() {
		Singer singer = singerService.getSinger(new Singer("jim", 77));
		Assert.assertEquals("zhuzhen", singer.getName());
		Assert.assertEquals(28, singer.getAge());
	}

	@Test
	public void hello() {
		Assert.assertEquals("hello: zhuzhen", singerService.hello("zhuzhen"));
	}

	// @Test
	// public void testDeleteInfo() {
	// infoService.deleteInfo("1");
	// }
	//
	// @Test
	// public void testGetInfo() {
	// Info info = infoService.getInfo("1");
	// Assert.assertEquals(info.getName(), "zhuzhen");
	// }
	//
	// @Test
	// public void testGetInfos() {
	// List<Info> list = infoService.getInfos();
	// Assert.assertEquals(2, list.size());
	// Assert.assertEquals("zhuzhen", list.get(0).getName());
	// Assert.assertEquals("guolili", list.get(1).getName());
	// }
	//
	// @Test
	// public void testSaveOrUpdateInfo() {
	// infoService.saveOrUpdateInfo(new Info("1", "zhuzhen", "hello zhuzhen"));
	// }
}
