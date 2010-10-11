package jsp.one;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 无参数无标签体的标签
 * 
 * @author Administrator
 * 
 */
public class SimpleTag extends TagSupport
{

	@Override
	public int doEndTag() throws JspException
	{
		try
		{
			pageContext.getOut().write("我是一个没有参数和标签体的标签");

		}
		catch (Exception e)
		{
			throw new JspTagException("����错误");
		}

		return EVAL_PAGE;// 返回装载池以后也可以用ؽ������Ժ����á�
	}

	@Override
	public int doStartTag() throws JspException
	{
		return super.doStartTag();
	}

}
