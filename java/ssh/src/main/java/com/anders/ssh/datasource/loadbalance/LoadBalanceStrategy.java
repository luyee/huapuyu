package com.anders.ssh.datasource.loadbalance;

public interface LoadBalanceStrategy<T> {

	T elect();

	void removeTarget(T t);

	void recoverTarget(T t);
}
