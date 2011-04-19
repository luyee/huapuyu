package chainOfResponsibility.example3;

public class MsgProcessor
{
	private String msg;
	private FilterChain fc;

	public String process()
	{
		return fc.doFilter(msg);
	}

	// getters and setters

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public FilterChain getFc()
	{
		return fc;
	}

	public void setFc(FilterChain fc)
	{
		this.fc = fc;
	}
}
