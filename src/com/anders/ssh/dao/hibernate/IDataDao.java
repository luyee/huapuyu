package com.anders.ssh.dao.hibernate;

import java.util.List;

import com.anders.ssh.model.xml.Data;

public interface IDataDao {
	public Data getById(Long id);

	public void save(Data data);

	public void update(Data data);

	public void delete(Data data);

	public void deleteById(Long id);

	public List<Data> getAll();
}
