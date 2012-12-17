package com.anders.ssh.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.service.DataService;

@Component("dataFacade")
public class DataFacadeImpl implements DataFacade {
	@Resource(name = "dataService")
	private DataService dataService;

	public void updateTest() {

		List<Data> list1 = dataService.getAll();

		Data data = new Data();
		data.setId(1L);
		data.setType(Byte.MIN_VALUE);
		data.setName("zhuzhen");
		data.setEnable(true);
		dataService.save(data);

		List<Data> list2 = dataService.getAll();

		System.out.println("hello world");
	}
}
