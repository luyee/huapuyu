package 枚举;

public class Tester
{
	public static void main(String[] args) throws Exception
	{
		Type t1 = Type.可用;
		Type t2 = Type.不可用;
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t1.getValue());
		System.out.println(t2.getValue());
		System.out.println(Type.可用.getValue());
		System.out.println(Type.不可用.getValue());
	}
}
