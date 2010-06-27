package struts.one.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class SubmitAction extends DispatchAction
{
	private CrudDao crudDao;
	private Pager pager;

	public void setCrudDao(CrudDao crudDao)
	{
		this.crudDao = crudDao;
	}

	public ActionForward retrieve(ActionMapping mapping, ActionForm argForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String sql = "select * from mmhouse";
		if (pager == null)
		{
			pager = crudDao.getPage(10, 0, sql);
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
		request.setAttribute("pager", pager);
		return mapping.findForward("pager");
	}
}
