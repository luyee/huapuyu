package struts.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.Data;

import org.apache.commons.beanutils.BeanUtils;

import struts.vo.DataVo;
import dao.hibernate.DataDao;

public class DataAction extends BaseAction
{
	private static final long serialVersionUID = -7548709763761263808L;

	private DataVo dataVo;
	private DataDao dataDao;
	private List<Data> dataList;
	private Integer id;

	public void init()
	{
		dataList = dataDao.getAll();
	}

	public String save()
	{
		Data data = new Data();
		try
		{
			BeanUtils.copyProperties(data, dataVo);
			data.setType(Byte.MIN_VALUE);
			dataDao.save(data);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String saveInput()
	{
		return INPUT;
	}

	public String list()
	{
		init();
		return SUCCESS;
	}

	public String delete()
	{
		dataDao.deleteById(id);
		return SUCCESS;
	}

	public String update()
	{
		Data data = new Data();
		try
		{
			BeanUtils.copyProperties(data, dataVo);
			data.setType(Byte.MIN_VALUE);
			dataDao.update(data);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String updateInput()
	{
		Data data = dataDao.getById(id);
		dataVo = new DataVo();
		try
		{
			BeanUtils.copyProperties(dataVo, data);
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return INPUT;
	}

	public DataVo getDataVo()
	{
		return dataVo;
	}

	public void setDataVo(DataVo dataVo)
	{
		this.dataVo = dataVo;
	}

	public DataDao getDataDao()
	{
		return dataDao;
	}

	public void setDataDao(DataDao dataDao)
	{
		this.dataDao = dataDao;
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
