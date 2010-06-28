package struts.one.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import struts.one.form.LoginForm;

public class SubmitAction extends DispatchAction
{
	private CrudDao crudDao;
	private Pager pager;
	private int pageSize = 10;

	public void setCrudDao(CrudDao crudDao)
	{
		this.crudDao = crudDao;
	}

	public ActionForward retrieve(ActionMapping mapping, ActionForm argForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		LoginForm form = (LoginForm) argForm;
		// 因为直接回车，page这个filed是不提交的
		try
		{
			if (form.getPageSize().equals("undefined"))
			{
				form.setPageSize(request.getParameter("pageSize").toString());
			}
		} catch (RuntimeException e1)
		{
		}
		String sql = "select * from mmhouse";
		if (pager == null || form.getPageSize() == null || pageSize != Integer.parseInt(form.getPageSize()))
		{
			try
			{
				pageSize = Integer.parseInt(request.getParameter("pageSize").toString());
			} catch (Exception e)
			{
			}
			pager = crudDao.getPage(pageSize, 0, sql);
		}
		else
		{
			int startIndex = 0;
			if (request.getParameter("startIndex") == null)
			{

			}
			else
			{
				try
				{
					startIndex = Integer.parseInt(request.getParameter("startIndex").toString());
				} catch (RuntimeException e)
				{
					startIndex = 0;
				}
			}
			if (startIndex > pager.getTotalCount() || startIndex < 0)
			{
				startIndex = 0;
			}
			pager.setStartIndex(startIndex);

		}
		List<Object> record = crudDao.getListFromSql(sql, pager.getStartIndex());
		pager.setItemList(record);
		pager.init();
		pager.setPageSize(pageSize);
		request.setAttribute("pager", pager);
		return mapping.findForward("pager");
	}
}
