package com.anders.ssh.struts.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


import org.apache.commons.beanutils.BeanUtils;

import com.anders.ssh.dao.hibernate.DataDao;
import com.anders.ssh.model.xml.Data;
import com.anders.ssh.struts.vo.DataVo;


public class DataAction extends BaseAction
{
	private static final long serialVersionUID = -7548709763761263808L;

	private DataVo dataVo;
	private DataDao dataDao;
	private List<Data> dataList;
	private Integer id;

	public String saveInput()
	{
		dataList = dataDao.getAll();
		return INPUT;
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

	public void validateSave()
	{
		dataList = dataDao.getAll();
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

	public String listInput()
	{
		dataList = dataDao.getAll();
		return INPUT;
	}

	public String list()
	{
		return SUCCESS;
	}

	public String delete()
	{
		dataDao.deleteById(id);
		return SUCCESS;
	}

	// getter and setter

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
