package 缺省适配模式;

public class MainTest
{
	public static void main(String[] args)
	{
		和尚 h = new 鲁智深();
		h.习武();
		System.out.println(h.getName());
	}
}
