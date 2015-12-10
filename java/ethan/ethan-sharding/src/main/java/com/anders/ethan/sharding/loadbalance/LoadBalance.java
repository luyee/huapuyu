package com.anders.ethan.sharding.loadbalance;

public interface LoadBalance<T> {

	T elect();

	// void removeTarget(T t);
	//
	// void recoverTarget(T t);
}
