package ¶àÀýÄ£Ê½;

public class MainClass
{
	public static void main(String[] args)
	{
		System.out.println(Multiton.getInstance(1).toString());
		System.out.println(Multiton.getInstance(1).toString());
		System.out.println(Multiton.getInstance(1).toString());
		System.out.println(Multiton.getInstance(2).toString());
		System.out.println(Multiton.getInstance(2).toString());
		System.out.println(Multiton.getInstance(2).toString());
	}
}
