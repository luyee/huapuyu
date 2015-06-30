package com.anders.ssh.aop;

public interface CustService {
	public String save(String name, int i);

	public void update(String name);

	public void get(String name, long i);

	public void delete(String name);
}
