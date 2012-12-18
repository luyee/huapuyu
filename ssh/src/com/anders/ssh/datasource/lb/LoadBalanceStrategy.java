package com.anders.ssh.datasource.lb;

public interface LoadBalanceStrategy<T> {

	T elect();

	void removeTarget(T t);

	void recoverTarget(T t);
}
