package dao.interf;

import java.util.List;

import model.Test;

public interface TestDao
{
	public Test getById(Integer id);

	public void save(Test entity);

	public void update(Test entity);

	public void delete(Test entity);

	public void deleteById(Integer id);

	public List<Test> getAll();
}