package com.anders.dp.行为模式.责任链.example;

/**
 * HTML过滤器
 * 
 * @author Anders
 * 
 */
public class HtmlFilter implements Filter {
	@Override
	public String doFilter(String msg) {
		System.out.println(this.getClass().getName());
		return msg.replace('<', '[').replace('>', ']');
	}
}
