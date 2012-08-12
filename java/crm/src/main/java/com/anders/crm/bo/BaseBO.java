package com.anders.crm.bo;

import java.io.Serializable;

/**
 * Base Business Object
 * 
 * @author Anders Zhu
 * 
 */
@SuppressWarnings("serial")
public abstract class BaseBO implements Serializable {

	@Override
	public abstract String toString();

	// @Override
	// public abstract boolean equals(Object object);

	// @Override
	// public abstract int hashCode();
}
