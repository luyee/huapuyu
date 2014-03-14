package com.anders.dp.责任链.example;


public class SesitiveFilter implements Filter
{
	@Override
	public String doFilter(String msg)
	{
		System.out.println(this.getClass().getName());
		return msg.replace("敏感", "");
	}
}
