package struts.one.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import struts.one.form.LoginForm;

public class LoginAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm argForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoginForm form = (LoginForm) argForm;
		if ("exception".equals(form.getName()))
		{
			throw new MyException("in my exception");
		}

		if ("chixuan".equals(form.getName()) && "1".equals(form.getPassWord()))
		{
			return mapping.findForward("submit");
		}
		else
		{
			return mapping.findForward("error");
		}
	}
}
