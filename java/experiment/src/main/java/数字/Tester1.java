package 数字;

public class Tester1 {

	public static void main(String[] args) {
		int x = 0;
		int y = 10;

		System.out.println((x + 1) / (y + 1));
		System.out.println((x + 1.0) / (y + 1.0));
		// 注意：如果不加1.0，则计算结果为整数，因为是整数运算，改为1.0，则是浮点运算
		if ((x + 1.0) / (y + 1.0) <= 0.1) {
			System.out.println(true);
		}
		else {
			System.out.println(false);
		}
	}

}
