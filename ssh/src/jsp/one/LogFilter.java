package jsp.one;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 可以写自己的log4J了
 * 
 * @author Administrator
 * 
 */
public class LogFilter implements Filter
{

	private FilterConfig config;

	@Override
	public void destroy()
	{
		this.config = null;
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException
	{

		ServletContext context = this.config.getServletContext();
		long before = System.currentTimeMillis();
		HttpServletRequest hrequest = (HttpServletRequest) arg0;
		context.log("����û������获得用户请求地址" + hrequest.getServletPath());
		try
		{
			// 到下一个Filter中��Filter��
			arg2.doFilter(arg0, arg1);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		long after = System.currentTimeMillis();
		context.log("���󱻶�请求被定为到��" + hrequest.getRequestURI());
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		this.config = arg0;
	}

}
