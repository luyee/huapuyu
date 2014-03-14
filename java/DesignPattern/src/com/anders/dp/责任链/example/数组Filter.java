package com.anders.dp.责任链.example;


public class 数组Filter
{
	private String msg;

	private Filter[] filters = { new HtmlFilter(), new SesitiveFilter() };

	public String process()
	{
		String tempMsg = msg;

		for (Filter filter : filters)
			tempMsg = filter.doFilter(tempMsg);

		return tempMsg;

	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
}
