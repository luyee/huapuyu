package com.anders.ssh.dao.mybatis;

import java.util.List;

import com.anders.ssh.bo.xml.Data;

public interface DataMapper {
	List<Data> getAll();

	Data getById(Long id);

	void save(Data data);

	void update(Data data);

	void deleteById(Long id);

	void delete(Data data);
}
