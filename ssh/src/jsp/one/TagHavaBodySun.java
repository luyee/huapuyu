package jsp.one;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 对于标签的返回值 SKIP_BODY 不处理标签体，直接调用doEndTag SKIP_PAGE 忽略标签后面的JSP页面 EVEL_PAGE
 * 处理结束，直接处理页面 EVAL_BODY_INCLUDE 处理标签体，但是忽略setBodyContent和doInitBpdy
 * EVAL_BODY_AGAIN 对标签体进行循环处理 由于循环，建议大断点跟一下
 * 
 * @author Administrator
 * 
 */
public class TagHavaBodySun extends TagSupport
{
	private String item;

	public String getItem()
	{
		return item;
	}

	public void setItem(String item)
	{
		this.item = item;
	}

	@Override
	public int doStartTag() throws JspException
	{
		try
		{
			pageContext.getOut().write((String) pageContext.getAttribute(item));

		} catch (Exception e)
		{
			throw new JspException("���出错");
		}
		return EVAL_PAGE;
	}
}
