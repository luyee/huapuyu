package 门面模式;

public class MainTest
{
	public static void main(String[] args)
	{
		SecurityFacade sf = new SecurityFacade();
		sf.activate();
	}
}
