package dao.interf;

import java.io.Serializable;
import java.util.List;

public interface IDao<PK extends Serializable, T>
{
	void save(T entity);

	void persist(T entity);

	void delete(T entity);

	void deleteById(PK id);

	void merge(T entity);

	void saveOrUpdate(T entity);

	void update(T entity);

	T getById(PK id);

	List<T> getAll();
}
