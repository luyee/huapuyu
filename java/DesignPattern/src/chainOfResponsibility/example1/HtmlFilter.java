package chainOfResponsibility.example1;

public class HtmlFilter implements Filter
{
	@Override
	public String doFilter(String msg)
	{
		return msg.replace('<', '[').replace('>', ']');
	}
}
