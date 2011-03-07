package struts.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements SessionAware
{
	private static final long serialVersionUID = 5846786151615011904L;

	private Map<String, Object> session;

	public void setSession(Map<String, Object> session)
	{
		this.session = session;
	}

	public Map<String, Object> getSession()
	{
		return this.session;
	}
}
