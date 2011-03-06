package tapestry.pages;

import model.Data;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import service.interf.DataService;

public class DataAdd
{
	@Property
	private Data data = new Data();

	@Inject
	private DataService dataService;

	public void onSuccess()
	{
		data.setName("tapestry");
		dataService.save(data);
	}
}
