package com.anders.ethan.sharding.jta.compensable;

import java.io.Serializable;

public interface Compensable<T extends Serializable> {

	public void confirm(T variable) throws CompensableException;

	public void cancel(T variable) throws CompensableException;

}
