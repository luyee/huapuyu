package com.anders.dp.责任链.example;

import java.util.ArrayList;
import java.util.List;

public class 列表Filter implements Filter
{
	private List<Filter> filters = new ArrayList<Filter>();

	public 列表Filter addFilter(Filter filter)
	{
		this.filters.add(filter);
		return this;
	}

	@Override
	public String doFilter(String msg)
	{
		for (Filter filter : filters)
			msg = filter.doFilter(msg);

		return msg;
	}
}
