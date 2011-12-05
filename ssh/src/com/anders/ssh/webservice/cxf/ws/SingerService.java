package com.anders.ssh.webservice.cxf.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SingerService
{
	@WebMethod
	public String hello(@WebParam
	String name);

	@WebMethod
	public Singer getUser(@WebParam
	Singer user);

	@WebMethod
	public List<Singer> getAllUser(@WebParam
	List<String> list);

	// @WebMethod
	// public Map<Integer, User> example3(@WebParam Map<Integer, String> m);
}