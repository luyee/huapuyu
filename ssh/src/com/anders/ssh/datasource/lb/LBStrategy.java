/**
 * 
 */
package com.anders.ssh.datasource.lb;

/**
 * DataSource load balancer select strategy
 * 
 * @author xiemalin
 * 
 */
public interface LBStrategy<T> {

	T elect();

	void removeTarget(T t);

	void recoverTarget(T t);

}
