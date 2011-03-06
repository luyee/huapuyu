package service.interf;

import java.util.List;

import model.Data;

public interface DataService
{
	public Data getById(Integer id);

	public void save(Data entity);

	public void update(Data entity);

	public void delete(Data entity);

	public void deleteById(Integer id);

	public List<Data> getAll();
}