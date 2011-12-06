package com.anders.ssh.webservice.cxf.rs;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InfoTest {
	private InfoService infoService;

	@Before
	public void setUp() {
		infoService = JAXRSClientFactory.create("http://127.0.0.1:5555/ssh/cxf/rs", InfoService.class);
		WebClient.client(infoService).accept(MediaType.APPLICATION_XML);
	}

	@Test
	public void testDeleteInfo() {
		infoService.deleteInfo("1");
	}

	@Test
	public void testGetInfo() {
		Info info = infoService.getInfo("1");
		Assert.assertEquals(info.getName(), "zhuzhen");
	}

	@Test
	public void testGetInfos() {
		List<Info> list = infoService.getInfos();
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("zhuzhen", list.get(0).getName());
		Assert.assertEquals("guolili", list.get(1).getName());
	}

	@Test
	public void testSaveOrUpdateInfo() {
		infoService.saveOrUpdateInfo(new Info("1", "zhuzhen", "hello zhuzhen"));
	}
}
