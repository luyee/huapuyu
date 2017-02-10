package com.anders.ssh.webservice.xfire;

import java.util.List;
import java.util.Map;

public interface ITest {
	public String example(String message);

	public User example1(User model);

	@SuppressWarnings("unchecked")
	public List example2(List l);

	@SuppressWarnings("unchecked")
	public Map example3(Map m);
}