package com.anders.dp.行为模式.不变;

public class Tester {
	public static void main(String[] args) {
		// String类是一个强不变类型
		String a = "abc";
		String b = "abc";
		String c = "abc";

		System.out.println(a == b);
		System.out.println(b == c);
		System.out.println(a == c);
	}
}
