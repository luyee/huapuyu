package unicode;

import org.apache.commons.lang.StringUtils;

public class Tester
{
	public static void main(String[] args) throws Exception
	{
		String str1 = "π˘¡¢¡¢∫Õ÷Ï’Ò";
		// String str1 = "ABCDEFGH";

		byte[] bytes1 = str1.getBytes();
		for (int i = 0; i < bytes1.length; i++)
		{
			System.out.print(bytes1[i] + " ");
		}
		System.out.println(StringUtils.EMPTY);

		for (int i = 0; i < bytes1.length; i++)
		{
			bytes1[i] = (byte) (bytes1[i] + 32);
			System.out.print(bytes1[i] + " ");
		}

		String str2 = new String(bytes1);
		System.out.println(str2);

		byte[] bytes2 = str2.getBytes();
		for (int i = 0; i < bytes2.length; i++)
		{
			bytes2[i] = (byte) (bytes2[i] - 32);
			System.out.print(bytes2[i] + " ");
		}
		System.out.println(new String(bytes2));

		byte[] bytes3 = { -71, -7 };
		byte[] bytes4 = { -43, -15 };
		System.out.println(new String(bytes3));
		System.out.println(new String(bytes4));
	}
}
