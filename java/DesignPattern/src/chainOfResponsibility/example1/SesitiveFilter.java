package chainOfResponsibility.example1;

public class SesitiveFilter implements Filter
{
	@Override
	public String doFilter(String msg)
	{
		return msg.replace("√Ù∏–", "");
	}
}
