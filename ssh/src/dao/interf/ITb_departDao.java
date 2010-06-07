package dao.interf;

import java.util.List;

import model.Tb_depart;

public interface ITb_departDao
{
	public Tb_depart get(int id);

	public void save(Tb_depart model);

	public void update(Tb_depart model);

	public void delete(Tb_depart model);

	public void deleteById(int id);

	@SuppressWarnings("unchecked")
	public List getAll();
}