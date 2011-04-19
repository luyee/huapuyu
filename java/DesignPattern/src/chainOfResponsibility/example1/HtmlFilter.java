package chainOfResponsibility.example1;

public class HtmlFilter implements Filter
{
	@Override
	public String doFilter(String msg)
	{
		System.out.println(this.getClass().getName());
		return msg.replace('<', '[').replace('>', ']');
	}
}
