package service.impl;

import service.interf.IIndexService;

public class IndexService implements IIndexService
{
	public boolean valid()
	{
		System.out.println("valid");
		return true;
	}
}
