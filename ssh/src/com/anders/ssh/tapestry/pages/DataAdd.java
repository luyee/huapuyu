package com.anders.ssh.tapestry.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.anders.ssh.bo.xml.Data;
import com.anders.ssh.service.DataService;

public class DataAdd {
	@Property
	private Data data = new Data();

	@Inject
	private DataService dataService;

	public void onSuccess() {
		data.setName("tapestry");
		dataService.save(data);
	}
}
