package com.anders.crm.service;

import java.io.Serializable;

public interface GenericService<PK extends Serializable, T> {
	void save(T entity);
}
