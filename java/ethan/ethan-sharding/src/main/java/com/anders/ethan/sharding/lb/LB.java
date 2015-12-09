package com.anders.ethan.sharding.lb;

public interface LB<T> {

	T elect();

	void removeTarget(T t);

	void recoverTarget(T t);
}
