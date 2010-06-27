package jsp1;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 有属性无标签体的标签
 * 
 * @author Administrator
 * 
 */
public class TagHavaProperties extends TagSupport
{

	private String name;
	private String pass;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPass()
	{
		return pass;
	}

	public void setPass(String pass)
	{
		this.pass = pass;
	}

	@Override
	public int doEndTag() throws JspException
	{

		Writer out = pageContext.getOut();
		try
		{
			out.write("<html>");
			out.write("<head>");
			out.write("<title>���ү</title>");
			out.write("</head>");
			out.write("<body>");
			out.write("我是有属性无标签体的标签�" + name + "<br/>");
			out.write("你的名字�������" + name + "<br/>");
			out.write("你的密码�������" + pass + "<br/>");
			out.write("啦啦啦啦啦啦啦啦啦");
			out.write("</body>");
			out.write("</html>");
		} catch (IOException e)
		{
			throw new JspTagException("错误����");
		}

		return EVAL_PAGE;
	}

	public void destory()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~关闭了！~~~~~~~~~~~~~~~~~~~~~");
	}

}
