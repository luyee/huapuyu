package cxf;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ITest
{
	@WebMethod
	public String example(@WebParam String message);

	@WebMethod
	public User example1(@WebParam User model);

	@WebMethod
	public List<User> example2(@WebParam List<String> l);

	// @WebMethod
	// public Map<Integer, User> example3(@WebParam Map<Integer, String> m);
}