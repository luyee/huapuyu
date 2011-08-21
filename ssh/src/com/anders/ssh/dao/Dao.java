package com.anders.ssh.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<PK extends Serializable, T>
{
	public T getById(PK id);

	public void save(T entity);

	public void update(T entity);

	public void delete(T entity);

	public void deleteById(PK id);

	public List<T> getAll();
}