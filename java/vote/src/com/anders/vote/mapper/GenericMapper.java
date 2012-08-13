package com.anders.vote.mapper;

import java.io.Serializable;
import java.util.List;

public interface GenericMapper<PK extends Serializable, T> {

	public T getById(final Long id);

	public List<T> getAll();

	public T findById(final Long id);

	public List<T> findAll();

	public void save(final T entity);

	public void update(final T entity);

	public void disabledById(final Long id);

	public void disabled(final T entity);

	public void deleteById(final Long id);

	public void delete(final T entity);
}
