package struts.one.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class LoginForm extends ActionForm
{
	private static final long serialVersionUID = 123396843686374620L;
	private String name;
	private String passWord;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassWord()
	{
		return passWord;
	}

	public void setPassWord(String passWord)
	{
		this.passWord = passWord;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{
		System.out.println("in the validation");
		ActionErrors error = new ActionErrors();
		if (StringUtils.isNotEmpty(this.name))
		{
			error.add("name", new ActionMessage("error.username.required"));
		}
		if (StringUtils.isNotEmpty(this.passWord))
		{
			error.add("passWord", new ActionMessage("error.password.required"));
		}
		// return error;
		return null;
	}
}
