package dao.interf;

import java.io.Serializable;

public interface IDao<PK extends Serializable, T>
{
	void save(T entity);

	void persist(T entity);

	void delete(T entity);

	void merge(T entity);

	void saveOrUpdate(T entity);

	T getById(PK id);
}
