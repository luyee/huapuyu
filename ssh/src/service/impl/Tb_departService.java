package service.impl;

import java.util.List;

import model.test.Tb_depart;
import service.interf.ITb_departService;
import dao.interf.ITb_departDao;

public class Tb_departService implements ITb_departService
{
	private ITb_departDao dao;

	public ITb_departDao getDao()
	{
		return this.dao;
	}

	public void setDao(ITb_departDao dao)
	{
		this.dao = dao;
	}

	@Override
	public void delete(Tb_depart model)
	{
		this.dao.delete(model);
	}

	@Override
	public void deleteById(int id)
	{
		this.dao.deleteById(id);
	}

	@Override
	public Tb_depart get(int id)
	{
		return this.dao.get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAll()
	{
		Tb_depart model = new Tb_depart();
		model.setName("cme");
		model.setEnabled(1);

		this.dao.save(model);

		return this.dao.getAll();
	}

	@Override
	public void save(Tb_depart model)
	{
		this.dao.save(model);
	}

	@Override
	public void update(Tb_depart model)
	{
		this.dao.update(model);
	}
}
