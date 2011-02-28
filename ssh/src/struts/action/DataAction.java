package struts.action;

import java.util.ArrayList;
import java.util.List;

import model.Data;

import com.opensymphony.xwork2.ActionSupport;

public class DataAction extends ActionSupport
{
	private static final long serialVersionUID = -7548709763761263808L;

	private Data data;
	private List<Data> dataList = new ArrayList<Data>();
	private Integer id;

	public void init()
	{
		Data data = new Data();
		data.setId(1);
		data.setName("zhuzhen");
		dataList.add(data);
		data = new Data();
		data.setId(2);
		data.setName("guolili");
		dataList.add(data);
	}

	public String add()
	{
		Data data = new Data();
		data.setId(3);
		data.setName("zhuzhen");
		dataList.add(data);
		return SUCCESS;
	}

	public String load()
	{
		if (id == 3)
		{
			data = new Data();
			data.setId(3);
			data.setName("zhuzhen");
		}
		return SUCCESS;
	}

	public String list()
	{
		init();
		return SUCCESS;
	}

	public String delete()
	{
		return SUCCESS;
	}

	public String edit()
	{
		return SUCCESS;
	}

	public Data getData()
	{
		return data;
	}

	public void setData(Data data)
	{
		this.data = data;
	}

	public List<Data> getDataList()
	{
		return dataList;
	}

	public void setDataList(List<Data> dataList)
	{
		this.dataList = dataList;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

}
