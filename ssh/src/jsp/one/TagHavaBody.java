package jsp.one;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TagHavaBody extends BodyTagSupport
{

	private List<String> items;
	private String bean;
	private String item;

	public String getItem()
	{
		return item;
	}

	public void setItem(String item)
	{
		this.item = item;
	}

	private int index;

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public String getBean()
	{
		return bean;
	}

	public void setBean(String bean)
	{
		this.bean = bean;
	}

	public List<String> getItems()
	{
		return items;
	}

	public void setItems(List<String> items)
	{
		this.items = items;
	}

	@Override
	public void doInitBody() throws JspException
	{
		Object obj = pageContext.getAttribute(bean);
		items = (List<String>) obj;// 由于向下转型不安全会发出警告，不知道如何破解这个黄线��֪������ƽ����?��
		item = items.get(index);
		pageContext.setAttribute("item", item);
	}

	@Override
	public int doStartTag() throws JspException
	{

		Object obj = pageContext.getAttribute(bean);
		items = (List<String>) obj;// �����֪������ƽ����?��
		item = items.get(index);
		pageContext.setAttribute("item", item);
		return EVAL_BODY_BUFFERED;// 对于这个index我们是要计算的�����
	}

	@Override
	public int doAfterBody() throws JspException
	{
		index++;
		if (index >= 10)
		{
			index = 0;
			return SKIP_BODY;// 不在计算标签贴，去调用doEndTagڼ����ǩ��ȥ����doEndTag
		}
		pageContext.setAttribute("item", items.get(index));
		return EVAL_BODY_AGAIN;// 循环计算标签体�������ǩ��
	}

	@Override
	public int doEndTag() throws JspException
	{
		try
		{
			bodyContent.writeOut(pageContext.getOut());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
