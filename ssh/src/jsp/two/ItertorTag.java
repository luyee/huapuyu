package jsp.two;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

//对于jsp2.0的标签已经不再需要写那些doStart和doEnd了，写doTag就好
public class ItertorTag extends SimpleTagSupport
{

	private String bean;

	public String getBean()
	{
		return bean;
	}

	public void setBean(String bean)
	{
		this.bean = bean;
	}

	@Override
	public void doTag() throws JspException, IOException
	{
		Collection<String> items = (Collection<String>) getJspContext().getAttribute(bean);
		for (String s : items)
		{
			getJspContext().setAttribute("item", s);
			getJspBody().invoke(null);// 输出到默认的jspWriter中，也可以重新定位Writer
		}
	}
}
